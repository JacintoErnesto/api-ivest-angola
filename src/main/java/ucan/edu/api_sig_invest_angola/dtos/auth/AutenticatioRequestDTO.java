package ucan.edu.api_sig_invest_angola.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AutenticatioRequestDTO(
        @NotBlank(message = "O campo username é um campo obrigatório")
        @Email(message = "O endereço de email informado não é válido.")
        @Size(min = 10, max = 30, message = "O campo username deve ter entre 10 e 30 caracteres")
        String username,
        @NotBlank(message = "O campo password é obrigatório")
        @Size(min = 10, max = 30, message = "O campo password deve ter entre 10 e 30 caracteres")
        String password) {
}
