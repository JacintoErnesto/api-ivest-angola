package ucan.edu.api_sig_invest_angola.services.plano_negocio.pergunta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ucan.edu.api_sig_invest_angola.dtos.plano_negocio.pergunta.PerguntaPlanoNegocioRequestDTO;
import ucan.edu.api_sig_invest_angola.dtos.plano_negocio.pergunta.PerguntaPlanoNegocioReturnDTO;

import java.util.List;

@Service
public interface PerguntaPlanoNegocioService {
    PerguntaPlanoNegocioReturnDTO criarPerguntaPlanoNegocio(PerguntaPlanoNegocioRequestDTO perguntaPlanoNegocioRequestDTO);
    PerguntaPlanoNegocioReturnDTO buscarPerguntaPlanoNegocioPorId(Long id);
    PerguntaPlanoNegocioReturnDTO atualizarPerguntaPlanoNegocio(Long id, PerguntaPlanoNegocioRequestDTO perguntaPlanoNegocioRequestDTO);
    void deletarPerguntaPlanoNegocio(Long id);
    Page<PerguntaPlanoNegocioReturnDTO> listarPerguntaPlanoNegocio(PageRequest pageRequest);
    List<PerguntaPlanoNegocioReturnDTO> listarTodasPerguntaPlanoNegocio();
}
