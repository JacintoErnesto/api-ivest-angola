package ucan.edu.api_sig_invest_angola.services.localidade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ucan.edu.api_sig_invest_angola.dtos.localidade.LocalidadeRequestDTO;
import ucan.edu.api_sig_invest_angola.dtos.localidade.LocalidadeReturnDTO;
import ucan.edu.api_sig_invest_angola.models.localidade.Localidade;

import java.awt.print.Pageable;
import java.util.List;

@Service
public interface LocalidadeService {
    List<LocalidadeReturnDTO> buscarTodos();
    List<LocalidadeReturnDTO> buscarTodosPai();
    List<LocalidadeReturnDTO> buscarLocalidadeFilhoPeloIdPai(String localidadeId);
    Page<LocalidadeReturnDTO> buscarTodosPaginados(PageRequest pageable);
    LocalidadeReturnDTO buscarPeloId(String id);
    Localidade buscarModelPeloId(String id);
    LocalidadeReturnDTO criar(LocalidadeRequestDTO localidadeRequestDTO);
    LocalidadeReturnDTO atualizar(String id, LocalidadeRequestDTO localidadeRequestDTO);
    void deletar(String id);
}
