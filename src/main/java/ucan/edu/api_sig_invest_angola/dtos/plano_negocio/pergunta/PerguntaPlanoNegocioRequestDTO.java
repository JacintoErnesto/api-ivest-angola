package ucan.edu.api_sig_invest_angola.dtos.plano_negocio.pergunta;

import jakarta.validation.constraints.NotBlank;

public record PerguntaPlanoNegocioRequestDTO(
        @NotBlank(message = "O campo pergunta não pode ser vazio")
        String pergunta
) {

}
