package ucan.edu.api_sig_invest_angola.services.plano_negocio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ucan.edu.api_sig_invest_angola.dtos.plano_negocio.PlanoNegocioRequestDTO;
import ucan.edu.api_sig_invest_angola.dtos.plano_negocio.PlanoNegocioReturnDTO;

@Service
public interface PlanoNegocioService {
    public PlanoNegocioReturnDTO criarPlanoNegocio(PlanoNegocioRequestDTO planoNegocioRequestDTO);
    public PlanoNegocioReturnDTO buscarPlanoNegocioPorId(Long id);
    public PlanoNegocioReturnDTO atualizarPlanoNegocio(Long id, PlanoNegocioRequestDTO planoNegocioRequestDTO);
    public void deletarPlanoNegocio(Long id);
    public Page<PlanoNegocioReturnDTO> listarPlanoNegocioPorAreaAtuacaoId(Long areaAtuacaoId, PageRequest pageRequest);
    public Page<PlanoNegocioReturnDTO> listarPlanoNegocio(PageRequest pageRequest);

}
