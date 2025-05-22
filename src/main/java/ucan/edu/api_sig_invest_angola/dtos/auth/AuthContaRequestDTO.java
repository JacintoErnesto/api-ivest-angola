package ucan.edu.api_sig_invest_angola.dtos.auth;

import jakarta.validation.constraints.*;
import ucan.edu.api_sig_invest_angola.enums.auth.TipoConta;

public record AuthContaRequestDTO(
        @NotBlank(message = "O campo username é um campo obrigatório")
        @Email(message = "O endereço de email informado não é válido.")
        @Size(min = 10, max = 30, message = "O campo username deve ter entre 10 e 30 caracteres")
        String username,
        @NotBlank(message = "O campo password é obrigatório")
        @Size(min = 10, max = 30, message = "O campo password deve ter entre 10 e 30 caracteres")
        String password,
        @NotBlank(message = "O campo nome é obrigatório")
        @Size(min = 10, max = 30, message = "O campo nome deve ter entre 10 e 30 caracteres")
        String nome,
        @NotBlank(message = "O campo nid é obrigatório")
        @Size(min = 5, max = 15, message = "O campo nif deve ter entre 5 e 15 caracteres")
        String nif,
        @NotNull(message = "O campo tipo conta é obrigatório")
        String tipoConta
) {
}
