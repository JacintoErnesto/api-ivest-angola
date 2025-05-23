package ucan.edu.api_sig_invest_angola.services.serviceImpls.area_atuacao;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ucan.edu.api_sig_invest_angola.dtos.area_atuacao.AreaAtuacaoRequestDTO;
import ucan.edu.api_sig_invest_angola.dtos.area_atuacao.AreaAtuacaoReturnDTO;
import ucan.edu.api_sig_invest_angola.exceptions.PortalBusinessException;
import ucan.edu.api_sig_invest_angola.models.area_atuacao.AreaAtuacao;
import ucan.edu.api_sig_invest_angola.repositories.area_atuacao.AreaATuacaoRepository;
import ucan.edu.api_sig_invest_angola.services.area_atuacao.AreaAtuacaoService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AreaAtuacaoServiceImpl implements AreaAtuacaoService {
    private final AreaATuacaoRepository areaAtuacaoRepository;

    @Override
    public List<AreaAtuacaoReturnDTO> buscarTodos() {
        List<AreaAtuacao> listaAreaAtuacao = this.areaAtuacaoRepository.findAllByOrderByDesignacaoAsc();
        if (listaAreaAtuacao != null && !listaAreaAtuacao.isEmpty())
            return listaAreaAtuacao.stream().map(this::mapearAreaAtuacaoDTO).toList();
        return null;
    }

    @Override
    public AreaAtuacaoReturnDTO mapearAreaAtuacaoDTO(AreaAtuacao areaAtuacao) {
        if (areaAtuacao != null) {
            return new AreaAtuacaoReturnDTO(
                    areaAtuacao.getId(),
                    areaAtuacao.getDesignacao(),
                    areaAtuacao.getDataRegistro()
            );
        }
        return null;
    }

    @Override
    public AreaAtuacaoReturnDTO buscarPorId(Long id) {
        if (id != null && id > 0) {
            Optional<AreaAtuacao> areaAtuacao = this.areaAtuacaoRepository.findById(id);
            return areaAtuacao.map(this::mapearAreaAtuacaoDTO).orElse(null);
        }
        return null;
    }

    @Override
    public AreaAtuacao buscarModelPorId(Long id) {
        if (id != null && id > 0) {
            Optional<AreaAtuacao> areaAtuacao = this.areaAtuacaoRepository.findById(id);
            return areaAtuacao.orElseThrow(()->new PortalBusinessException("Área de Atuação não encontrada para o ID informado :" + id));
        }
        throw new PortalBusinessException("ID inválido");
    }

    @Override
    @Transactional
    public AreaAtuacaoReturnDTO criar(AreaAtuacaoRequestDTO areaAtuacaoRequestDTO) {

            AreaAtuacao areaAtuacao = new AreaAtuacao();
            validarAreaAtuacao(areaAtuacaoRequestDTO.designacao());
            areaAtuacao.setDesignacao(areaAtuacaoRequestDTO.designacao());
            areaAtuacao.setDataRegistro(LocalDateTime.now());
            this.areaAtuacaoRepository.save(areaAtuacao);
            return mapearAreaAtuacaoDTO(areaAtuacao);

    }

    private void validarAreaAtuacao(String areaAtuacao) {
        if (areaAtuacao == null || areaAtuacao.isEmpty())
            throw new PortalBusinessException("O campo designacao é obrigatório");
        else if (this.areaAtuacaoRepository.findByDesignacao(areaAtuacao).isPresent())
            throw new PortalBusinessException("Esta Area de atuacação já está cadastrado");
    }

    @Override
    public AreaAtuacaoReturnDTO atualizar(Long id, AreaAtuacaoRequestDTO areaAtuacaoRequestDTO) {
        Optional<AreaAtuacao> atuacao = this.areaAtuacaoRepository.findById(id);
        if (atuacao.isPresent()){
            AreaAtuacao areaAtuacao = atuacao.get();
            validarAreaAtuacao(areaAtuacaoRequestDTO.designacao());
            areaAtuacao.setDesignacao(areaAtuacaoRequestDTO.designacao());
            this.areaAtuacaoRepository.save(areaAtuacao);
            return mapearAreaAtuacaoDTO(areaAtuacao);
        }
        return null;
    }
    @Override
    public void deletar(Long id) {
        AreaAtuacao atuacao = areaAtuacaoRepository.findById(id)
                .orElseThrow(() -> new PortalBusinessException("Área de Atuação não encontrada para o ID informado :" +id));
        areaAtuacaoRepository.delete(atuacao);

    }

}
