package ucan.edu.api_sig_invest_angola.dtos.localidade;

import jakarta.validation.constraints.NotBlank;

public record LocalidadeRequestDTO(
        @NotBlank(message = "O campo id é obrigatório")
        String id,
        @NotBlank(message = "O campo designacao é obrigatório")
        String designacao,
        String localidadeId
) {
}
