package ucan.edu.api_sig_invest_angola.dtos.localidade;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record LocalidadeReturnDTO(

        String id,
        String designacao,
        String localidade,
        LocalDateTime dataRegisto
) {
}
