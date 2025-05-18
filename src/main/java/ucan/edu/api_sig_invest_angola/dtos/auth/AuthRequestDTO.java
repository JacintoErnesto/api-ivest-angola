package ucan.edu.api_sig_invest_angola.dtos.auth;

import jakarta.validation.constraints.*;
import ucan.edu.api_sig_invest_angola.enums.auth.TipoConta;

public record AuthRequestDTO(
        @NotBlank(message = "O campo username é um campo obrigatório")
        @Email(message = "O endereço de email informado não é válido.")
        @Size(min = 10, max = 30, message = "O campo username deve ter entre 10 e 30 caracteres")
        String username,
        @NotBlank(message = "O campo password é obrigatório")
        @Size(min = 10, max = 30, message = "O campo password deve ter entre 10 e 30 caracteres")
        String password,
        @NotNull(message = "O campo tipo conta é obrigatório")
        TipoConta tipoConta
) {
}
