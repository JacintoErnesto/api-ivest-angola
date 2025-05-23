package ucan.edu.api_sig_invest_angola.dtos.plano_negocio.template;

import ucan.edu.api_sig_invest_angola.dtos.area_atuacao.AreaAtuacaoReturnDTO;

import java.time.LocalDateTime;

public record TemplateNegocioReturnDTO(
        Long id,
        String codigoTemplatePlanoNegocio,
        TemplatePlanoNegocioGeralReturnDTO templatePlanoNegocioGeralReturnDTO,
        AreaAtuacaoReturnDTO areaAtuacaoReturnDTO,
        LocalDateTime localDateTime
) {
}
