package ucan.edu.api_sig_invest_angola.services.plano_negocio;

import org.springframework.stereotype.Service;
import ucan.edu.api_sig_invest_angola.dtos.plano_negocio.PlanoNegocioRequestDTO;
import ucan.edu.api_sig_invest_angola.dtos.plano_negocio.PlanoNegocioReturnDTO;

@Service
public interface PlanoNegocioService {
    public PlanoNegocioReturnDTO create(PlanoNegocioRequestDTO planoNegocioRequestDTO);
    public PlanoNegocioReturnDTO update(Long id, PlanoNegocioRequestDTO planoNegocioRequestDTO);
    public void delete(Long id);
    public PlanoNegocioReturnDTO buscarPorId(Long id);
    public PlanoNegocioReturnDTO buscarPorIdUsuario(Long idUsuario);
    public PlanoNegocioReturnDTO buscarPorIdAreaDeAtuacao(Long idAreaDeAtuacao);
    public String gerarPlanoNegocio(PlanoNegocioRequestDTO planoNegocioRequestDTO);

}
