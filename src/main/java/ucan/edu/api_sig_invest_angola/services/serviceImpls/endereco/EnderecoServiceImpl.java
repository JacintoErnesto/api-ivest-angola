package ucan.edu.api_sig_invest_angola.services.serviceImpls.endereco;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import ucan.edu.api_sig_invest_angola.dtos.endereco.EnderecoRequestDTO;
import ucan.edu.api_sig_invest_angola.dtos.endereco.EnderecoReturnDTO;
import ucan.edu.api_sig_invest_angola.exceptions.PortalBusinessException;
import ucan.edu.api_sig_invest_angola.models.endereco.Endereco;
import ucan.edu.api_sig_invest_angola.repositories.endereco.EnderecoRepository;
import ucan.edu.api_sig_invest_angola.services.endereco.EnderecoService;
import ucan.edu.api_sig_invest_angola.services.localidade.LocalidadeService;
import ucan.edu.api_sig_invest_angola.utils.Utils;
import ucan.edu.api_sig_invest_angola.utils.UtilsMessages.MessageUtils;

import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class EnderecoServiceImpl implements EnderecoService {
    private final EnderecoRepository enderecoRepository;
    private final LocalidadeService localidadeService;

    @Override
    @Transactional
    public EnderecoReturnDTO create(EnderecoRequestDTO enderecoRequestDTO) {
        try {
            Endereco endereco = new Endereco();
            validarCamposObrigatorio(enderecoRequestDTO.nomeRua());
            validarCamposObrigatorio(enderecoRequestDTO.localidade());
            validarCamposObrigatorio(enderecoRequestDTO.nomeBairro());
            log.info("Todos os campos foram validados");
            Utils.copyObjecto(enderecoRequestDTO, endereco);
            log.info("Todos os campos foram copiados");
            endereco.setLocalidade(localidadeService.buscarModelPeloId(enderecoRequestDTO.localidade()));
            log.info("Localidade foi definida");
            endereco.setDataRegsitro(LocalDateTime.now());
            log.info("Data de registro foi definida");
            this.enderecoRepository.save(endereco);
            log.info("Endereco foi salvo com sucesso");
            return mapParaEnderecoDTO(endereco);
        } catch (PortalBusinessException e) {
            throw new PortalBusinessException(e.getMessage());
        }

    }

    @Override
    public EnderecoReturnDTO update(Long id, @ModelAttribute EnderecoRequestDTO enderecoRequestDTO) {
        Endereco endereco = this.enderecoRepository.findById(id).orElseThrow(() -> new PortalBusinessException("Endereco não encontrado"));
        if (isPreenchido(enderecoRequestDTO.nomeRua()))
            endereco.setNomeRua(enderecoRequestDTO.nomeRua());
        if (isPreenchido(enderecoRequestDTO.nomeBairro()))
            endereco.setNomeBairro(enderecoRequestDTO.nomeBairro());
        if (isPreenchido(enderecoRequestDTO.numeroCasa()))
            endereco.setNumeroCasa(enderecoRequestDTO.numeroCasa());
        if (isPreenchido(enderecoRequestDTO.localidade()))
            endereco.setLocalidade(localidadeService.buscarModelPeloId(enderecoRequestDTO.localidade()));
        this.enderecoRepository.save(endereco);
        return mapParaEnderecoDTO(endereco);
    }

    @Override
    public void delete(Long id) {
        Endereco endereco = this.enderecoRepository.findById(id)
                .orElseThrow(() -> new PortalBusinessException(
                        MessageUtils.getMessage("sem.nada.no.retorno")));
        this.enderecoRepository.delete(endereco);
    }

    @Override
    public Page<EnderecoReturnDTO> buscarTodos(PageRequest pageRequest) {
        Page<Endereco> enderecoPage = this.enderecoRepository.findAll(pageRequest);
        if (!enderecoPage.isEmpty())
            return enderecoPage.map(this::mapParaEnderecoDTO);
        return null;
    }

    @Override
    public EnderecoReturnDTO buscarPorId(Long id) {
        Endereco endereco = this.enderecoRepository.findById(id).orElse((null));
        return mapParaEnderecoDTO(endereco);
    }

    @Override
    public Endereco buscarModelPeloId(Long id) {
        return this.enderecoRepository.findById(id).orElse(null);
    }

    @Override
    public Page<EnderecoReturnDTO> buscarPorLocalidade(String localidade, PageRequest pageRequest) {
        Page<Endereco> enderecoPage = this.enderecoRepository
                .findAllByLocalidadeContainingIgnoreCase(localidade, pageRequest);
        if (!enderecoPage.isEmpty())
            return enderecoPage.map(this::mapParaEnderecoDTO);
        return null;
    }

    @Override
    public Page<EnderecoReturnDTO> buscarPorNomeRua(String nomeRua, PageRequest pageRequest) {
        Page<Endereco> enderecoPage = this.enderecoRepository.findAllByNomeRuaContainingIgnoreCase(nomeRua, pageRequest);
        if (!enderecoPage.isEmpty())
            return enderecoPage.map(this::mapParaEnderecoDTO);
        return null;
    }

    @Override
    public Page<EnderecoReturnDTO> buscarPorNomeBairo(String nomeBairro, PageRequest pageRequest) {
        Page<Endereco> enderecoPage = this.enderecoRepository.findAllByNomeBairroContainingIgnoreCase(nomeBairro, pageRequest);
        if (!enderecoPage.isEmpty())
            return enderecoPage.map(this::mapParaEnderecoDTO);
        return null;
    }
    @Override
    public EnderecoReturnDTO mapParaEnderecoDTO(Endereco endereco) {
        if (endereco == null) {
            return null;
        }
        return new EnderecoReturnDTO(
                endereco.getId(),
                endereco.getNomeBairro(),
                endereco.getNomeRua(),
                endereco.getNumeroCasa(),
                endereco.getLocalidade() != null ? endereco.getLocalidade().getId() : null,
                endereco.getDataRegsitro()
        );
    }

    public Endereco mapParaEndereco(EnderecoReturnDTO enderecoReturnDTO) {
        Endereco endereco = new Endereco();
        endereco.setId(enderecoReturnDTO.id());
        endereco.setNomeBairro(enderecoReturnDTO.nomeBairro());
        endereco.setNomeRua(enderecoReturnDTO.nomeRua());
        endereco.setNumeroCasa(enderecoReturnDTO.numeroCasa());
        endereco.setLocalidade(localidadeService.buscarModelPeloId(enderecoReturnDTO.localidade()));
        return endereco;
    }

    private void validarCamposObrigatorio(String atributo) {
        if (atributo == null || atributo.isBlank()) {
            throw new PortalBusinessException("Campo " + atributo + " obrigatório não preenchido");
        }
    }

    private boolean isPreenchido(String atributo) {
        return atributo != null && !atributo.isBlank();
    }
}
