package ucan.edu.api_sig_invest_angola.services.area_atuacao;

import org.springframework.stereotype.Service;
import ucan.edu.api_sig_invest_angola.dtos.area_atuacao.AreaAtuacaoRequestDTO;
import ucan.edu.api_sig_invest_angola.dtos.area_atuacao.AreaAtuacaoReturnDTO;
import ucan.edu.api_sig_invest_angola.models.area_atuacao.AreaAtuacao;

import java.util.List;

@Service
public interface AreaAtuacaoService {
    public List<AreaAtuacaoReturnDTO> buscarTodos();
    public AreaAtuacaoReturnDTO buscarPorId(Long id);
    public AreaAtuacao buscarModelPorId(Long id);
    public  AreaAtuacaoReturnDTO criar(AreaAtuacaoRequestDTO areaAtuacaoRequestDTO);
    public AreaAtuacaoReturnDTO atualizar(Long id, AreaAtuacaoRequestDTO areaAtuacaoRequestDTO);
    public void deletar(Long id);
    public AreaAtuacaoReturnDTO mapearAreaAtuacaoDTO(AreaAtuacao areaAtuacao);
}
