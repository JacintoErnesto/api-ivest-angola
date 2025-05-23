package ucan.edu.api_sig_invest_angola.services.plano_negocio.template;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ucan.edu.api_sig_invest_angola.dtos.plano_negocio.template.TemplatePlanoNegocioRequestDTO;
import ucan.edu.api_sig_invest_angola.dtos.plano_negocio.template.TemplateNegocioReturnDTO;

@Service
public interface TemplatePlanoNegocioService {

    TemplateNegocioReturnDTO criarTemplatePlanoNegocio(TemplatePlanoNegocioRequestDTO templatePlanoNegocioRequestDTO);
    Page<TemplateNegocioReturnDTO> listarTemplatePlanoNegocio(PageRequest pageRequest);
    TemplateNegocioReturnDTO buscarTemplatePlanoNegocioPorId(Long id);
    TemplateNegocioReturnDTO atualizarTemplatePlanoNegocio(Long id, TemplatePlanoNegocioRequestDTO templatePlanoNegocioRequestDTO);
    void deletarTemplatePlanoNegocio(Long id);
    Page<TemplateNegocioReturnDTO> listarTemplatePlanoNegocioPorAreaAtuacao(Long areaAtuacaoId, PageRequest pageRequest);

}
