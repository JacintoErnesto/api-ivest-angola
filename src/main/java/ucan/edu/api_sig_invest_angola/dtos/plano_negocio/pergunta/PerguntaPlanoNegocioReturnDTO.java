package ucan.edu.api_sig_invest_angola.dtos.plano_negocio.pergunta;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record PerguntaPlanoNegocioReturnDTO(
        Long id,
        String pergunta,
        LocalDateTime dataCadastro
) {

}
