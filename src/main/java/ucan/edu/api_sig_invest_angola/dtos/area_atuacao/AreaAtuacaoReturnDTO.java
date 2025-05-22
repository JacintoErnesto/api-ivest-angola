package ucan.edu.api_sig_invest_angola.dtos.area_atuacao;

import java.time.LocalDateTime;

public record AreaAtuacaoReturnDTO(
        Long id,
        String designacao,
        LocalDateTime dataRegistro
) {
}
