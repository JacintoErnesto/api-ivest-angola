package ucan.edu.api_sig_invest_angola.services.serviceImpls.localidade;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ucan.edu.api_sig_invest_angola.dtos.localidade.LocalidadeRequestDTO;
import ucan.edu.api_sig_invest_angola.dtos.localidade.LocalidadeReturnDTO;
import ucan.edu.api_sig_invest_angola.exceptions.PortalBusinessException;
import ucan.edu.api_sig_invest_angola.models.localidade.Localidade;
import ucan.edu.api_sig_invest_angola.repositories.localidade.LocalidadeRepository;
import ucan.edu.api_sig_invest_angola.services.localidade.LocalidadeService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocalidadeServiceImpl implements LocalidadeService {

    private final LocalidadeRepository localidadeRepository;

    @Override
    public List<LocalidadeReturnDTO> buscarTodos() {
        List<Localidade> localidadeList = localidadeRepository.findAllByOrderByDesignacaoAsc();
        System.out.println("localidadeList = " + localidadeList);
        return localidadeList.stream()
                .map(localidade -> new LocalidadeReturnDTO(
                        localidade.getId(),
                        localidade.getDesignacao(),
                        localidade.getLocalidade() != null ? localidade.getLocalidade().getId() : null,
                        localidade.getDataRegisto()
                )).toList();
    }

    @Override
    public List<LocalidadeReturnDTO> buscarTodosPai() {
        List<Localidade> localidadeList = localidadeRepository.findAllByLocalidadeIsNullOrderByDesignacaoAsc();
        return localidadeList.stream()
                .map(localidade -> new LocalidadeReturnDTO(
                        localidade.getId(),
                        localidade.getDesignacao(),
                        localidade.getLocalidade() != null ? localidade.getLocalidade().getId() : null,
                        localidade.getDataRegisto()
                )).toList();
    }

    @Override
    public List<LocalidadeReturnDTO> buscarLocalidadeFilhoPeloIdPai(String localidadeId) {
        List<Localidade> localidadeIdfilho = localidadeRepository.findAllByLocalidadeId(localidadeId);
        if (localidadeIdfilho != null && !localidadeIdfilho.isEmpty())
            return localidadeIdfilho.stream().map(this::mapearLocalidadeParaDTO).toList();
        return null;
    }

    @Override
    public Page<LocalidadeReturnDTO> buscarTodosPaginados(PageRequest pageable) {
        Page<Localidade> localidadePage = localidadeRepository.findAll(pageable);
        if (!localidadePage.isEmpty())
            return localidadePage.map(localidade -> new LocalidadeReturnDTO(
                localidade.getId(),
                localidade.getDesignacao(),
                localidade.getLocalidade() != null ? localidade.getLocalidade().getId() : null,
                localidade.getDataRegisto()
        ));
        return null;
    }

    @Override
    public LocalidadeReturnDTO buscarPeloId(String id) {
        validarCamposObrigatorio(id);
        Optional<Localidade> localidadeOptional = localidadeRepository.findById(id);
        if (localidadeOptional.isPresent()) {
            Localidade localidade = localidadeOptional.get();
            return mapearLocalidadeParaDTO(localidade);
        }
        return null;
    }

    @Override
    public Localidade buscarModelPeloId(String id) {
        return this.localidadeRepository.findById(id).
                orElseThrow(()-> new PortalBusinessException("Localidade não encontrada"));
    }

    @Override
    @Transactional
    public LocalidadeReturnDTO criar(LocalidadeRequestDTO localidadeRequestDTO) {
        try {
            Localidade localidade = new Localidade();
            validarId(localidadeRequestDTO.id());
            validarDesignacao(localidadeRequestDTO.designacao());
            localidade.setId(localidadeRequestDTO.id());
            localidade.setDesignacao(localidadeRequestDTO.designacao());
            localidade.setLocalidade(validarLocalidadePai(localidadeRequestDTO.id(),localidadeRequestDTO.localidadeId()));
            localidade.setDataRegisto(LocalDateTime.now());
            this.localidadeRepository.save(localidade);
            return mapearLocalidadeParaDTO(localidade);
        } catch (PortalBusinessException e) {
            throw new PortalBusinessException(e.getMessage());
        }

    }

    private void validarId(String id) {
        if (id == null || id.isEmpty()) {
            throw new PortalBusinessException("O ID da localidade não pode ser nulo ou vazio");
        }
        if (isIdHierarquicoValido(id)) {
            throw new PortalBusinessException("O ID da localidade deve estar no formato hierárquico (ex: 1, 1.1, 1.1.1)");
        }
    }
    private void validarDesignacao(String designacao) {
        if (designacao == null || designacao.isEmpty()) {
            throw new PortalBusinessException("A designação da localidade não pode ser nula ou vazia");
        }
        if (this.localidadeRepository.findByDesignacao(designacao).isPresent()) {
            throw new PortalBusinessException("A designação da localidade já existe");
        }
    }
    private Localidade validarLocalidadePai(String id, String localidadeId) {

        if (localidadeId != null && !localidadeId.isEmpty()) {
            validarObrigatoriedadeLocalidadeIdeloId(id, localidadeId);
            Optional<Localidade> localidadeOptional = localidadeRepository.findById(localidadeId);
            if (localidadeOptional.isEmpty()) {
                throw new PortalBusinessException("A localidade pai não existe");
            }
            return localidadeOptional.get();
        }
        return null;
    }

    public boolean isIdHierarquicoValido(String id) {
        return id == null || !id.matches("^\\d+(\\.\\d+)*$");
    }

    @Override
    public LocalidadeReturnDTO atualizar(String id, LocalidadeRequestDTO localidadeRequestDTO) {
        Localidade localidade = localidadeRepository.findById(id)
                .orElseThrow(() -> new PortalBusinessException("Localidade não encontrada"));
        if (isPreenchido(localidadeRequestDTO.designacao())) {
            localidade.setDesignacao(localidadeRequestDTO.designacao());
        }
        if (isPreenchido(localidadeRequestDTO.id())) {
            if (isIdHierarquicoValido(localidadeRequestDTO.id())) {
                throw new PortalBusinessException("O ID da localidade deve estar no formato hierárquico (ex: 1, 1.1, 1.1.1)");
            }
            localidade.setId(localidadeRequestDTO.id());
        }
        if (isPreenchido(localidadeRequestDTO.localidadeId())) {
            localidade.setLocalidade(validarLocalidadePai(id, localidadeRequestDTO.localidadeId()));
        }
        this.localidadeRepository.save(localidade);

        return mapearLocalidadeParaDTO(localidade);
    }

    private void validarObrigatoriedadeLocalidadeIdeloId(String id,String localidadeId) {
        if(id.contains(".") && (localidadeId == null || localidadeId.isEmpty())) {
            throw new PortalBusinessException("O ID da localidade pai não pode ser nulo");
        }
    }
    @Override
    public void deletar(String id) {
        Localidade localidade = localidadeRepository.findById(id)
                .orElseThrow(() -> new PortalBusinessException("Localidade não encontrada"));
        this.localidadeRepository.delete(localidade);

    }
    private void validarCamposObrigatorio(String atributo){
        if (atributo == null || atributo.isBlank()){
            throw new PortalBusinessException("Campo " +atributo+ " obrigatório não preenchido");
        }
    }

    private boolean isPreenchido(String atributo) {
        return atributo != null && !atributo.isBlank();
    }
    private LocalidadeReturnDTO mapearLocalidadeParaDTO(Localidade localidade) {
        return new LocalidadeReturnDTO(
                localidade.getId(),
                localidade.getDesignacao(),
                localidade.getLocalidade() != null ? localidade.getLocalidade().getId() : null,
                localidade.getDataRegisto()
        );
    }
}
