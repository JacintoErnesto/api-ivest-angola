package ucan.edu.api_sig_invest_angola.dtos.area_atuacao;

import jakarta.validation.constraints.NotBlank;

public record AreaAtuacaoRequestDTO(
        @NotBlank String designacao
) {
}
