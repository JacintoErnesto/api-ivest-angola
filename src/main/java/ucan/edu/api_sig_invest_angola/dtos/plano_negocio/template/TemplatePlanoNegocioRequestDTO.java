package ucan.edu.api_sig_invest_angola.dtos.plano_negocio.template;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TemplatePlanoNegocioRequestDTO(
        @NotBlank(message = "O campo templatePlanoNegocioJson não deve estar vazio")
        String templatePlanoNegocioJson,
        @NotNull(message = "O campo area de atuação não deve estar vazio")
        @Positive(message = "O campo ordem da pergunta deve ser positivo")
        Long areaAtuacaoId,
        String codigoTemplatePlanoNegocio
) {
}
