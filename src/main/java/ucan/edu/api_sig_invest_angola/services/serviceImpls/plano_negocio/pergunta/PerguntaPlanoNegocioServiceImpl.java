package ucan.edu.api_sig_invest_angola.services.serviceImpls.plano_negocio.pergunta;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import ucan.edu.api_sig_invest_angola.dtos.plano_negocio.pergunta.PerguntaPlanoNegocioRequestDTO;
import ucan.edu.api_sig_invest_angola.dtos.plano_negocio.pergunta.PerguntaPlanoNegocioReturnDTO;
import ucan.edu.api_sig_invest_angola.exceptions.PortalBusinessException;
import ucan.edu.api_sig_invest_angola.models.plano_negocio.pergunta.PerguntaPlanoNegocio;
import ucan.edu.api_sig_invest_angola.repositories.plano_negocio.pergunta.PerguntaPlanoNegocioRepository;
import ucan.edu.api_sig_invest_angola.services.plano_negocio.pergunta.PerguntaPlanoNegocioService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PerguntaPlanoNegocioServiceImpl implements PerguntaPlanoNegocioService {
    private final PerguntaPlanoNegocioRepository perguntaPlanoNegocioRepository;
    @Override
    @Transactional
    public PerguntaPlanoNegocioReturnDTO criarPerguntaPlanoNegocio(PerguntaPlanoNegocioRequestDTO perguntaPlanoNegocioRequestDTO) {
        try {
            validarCamposObrigatorios(perguntaPlanoNegocioRequestDTO);
            if (this.buscarPerguntaPlanoNegocioPorPergunta(perguntaPlanoNegocioRequestDTO.pergunta()) != null) {
                throw new PortalBusinessException("Já existe uma pergunta com esse nome.");
            }
            PerguntaPlanoNegocio perguntaPlanoNegocio = converterParaModel(perguntaPlanoNegocioRequestDTO);
            PerguntaPlanoNegocio perguntaPlanoNegocioSalva = this.perguntaPlanoNegocioRepository.save(perguntaPlanoNegocio);
            return converterParaDTO(perguntaPlanoNegocioSalva);
        } catch (PortalBusinessException e) {
            throw new PortalBusinessException(e.getMessage());
        }

    }

    @Override
    public PerguntaPlanoNegocioReturnDTO buscarPerguntaPlanoNegocioPorId(Long id) {
        PerguntaPlanoNegocio perguntaPlanoNegocio = this.perguntaPlanoNegocioRepository.findById(id).orElse(null);
        return converterParaDTO(perguntaPlanoNegocio);
    }

    @Override
    @Transactional
    public PerguntaPlanoNegocioReturnDTO atualizarPerguntaPlanoNegocio(Long id, PerguntaPlanoNegocioRequestDTO perguntaPlanoNegocioRequestDTO) {
        try {
            PerguntaPlanoNegocio perguntaPlanoNegocio = this.perguntaPlanoNegocioRepository.findById(id).orElse(null);
            if (perguntaPlanoNegocio == null) {
                throw new PortalBusinessException("Pergunta não encontrada.");
            }
            if (isPreenchido(perguntaPlanoNegocioRequestDTO.pergunta()))
                perguntaPlanoNegocio.setPergunta(perguntaPlanoNegocioRequestDTO.pergunta());
            if (perguntaPlanoNegocio.getPergunta().equals(perguntaPlanoNegocioRequestDTO.pergunta())) {
                throw new PortalBusinessException("A pergunta não pode ser igual a anterior.");
            }
            PerguntaPlanoNegocio perguntaPlanoNegocioAtualizada = this.perguntaPlanoNegocioRepository.save(perguntaPlanoNegocio);
            return converterParaDTO(perguntaPlanoNegocioAtualizada);
        } catch (PortalBusinessException e) {
            throw new PortalBusinessException(e.getMessage());
        }

    }

    @Override
    public void deletarPerguntaPlanoNegocio(Long id) {
        PerguntaPlanoNegocio perguntaPlanoNegocio = this.perguntaPlanoNegocioRepository.findById(id).orElse(null);
        if (perguntaPlanoNegocio == null) {
            throw new PortalBusinessException("Pergunta não encontrada.");
        }
        this.perguntaPlanoNegocioRepository.delete(perguntaPlanoNegocio);
    }

    @Override
    public Page<PerguntaPlanoNegocioReturnDTO> listarPerguntaPlanoNegocio(PageRequest pageRequest) {
        Page<PerguntaPlanoNegocio> perguntaPlanoNegocioPage = this.perguntaPlanoNegocioRepository.findAll(pageRequest);
        if (!perguntaPlanoNegocioPage.isEmpty())
            return perguntaPlanoNegocioPage.map(this::converterParaDTO);
        return null;
    }

    @Override
    public List<PerguntaPlanoNegocioReturnDTO> listarTodasPerguntaPlanoNegocio() {
        return List.of();
    }

    private PerguntaPlanoNegocioReturnDTO converterParaDTO(PerguntaPlanoNegocio perguntaPlanoNegocio) {
        if (perguntaPlanoNegocio == null) {
            return null;
        }
        return new PerguntaPlanoNegocioReturnDTO(
                perguntaPlanoNegocio.getId(),
                perguntaPlanoNegocio.getPergunta(),
                perguntaPlanoNegocio.getDataCadastro()
                );
    }
    private PerguntaPlanoNegocio converterParaModel(PerguntaPlanoNegocioRequestDTO perguntaPlanoNegocioRequestDTO) {
        if (perguntaPlanoNegocioRequestDTO == null) {
            return null;
        }
        return new PerguntaPlanoNegocio(
                perguntaPlanoNegocioRequestDTO.pergunta(),
                LocalDateTime.now()
        );
    }
    private void validarCamposObrigatorios(PerguntaPlanoNegocioRequestDTO perguntaPlanoNegocioRequestDTO) {
        if (perguntaPlanoNegocioRequestDTO.pergunta() == null || perguntaPlanoNegocioRequestDTO.pergunta().isEmpty()) {
            throw new PortalBusinessException("O campo pergunta é obrigatório.");
        }
    }

    private boolean isPreenchido(String campo) {
        return campo != null && !campo.isBlank();
    }

    private PerguntaPlanoNegocio buscarPerguntaPlanoNegocioPorPergunta(String pergunta) {
        return this.perguntaPlanoNegocioRepository.findByPergunta(pergunta).orElse(null);
    }

}
