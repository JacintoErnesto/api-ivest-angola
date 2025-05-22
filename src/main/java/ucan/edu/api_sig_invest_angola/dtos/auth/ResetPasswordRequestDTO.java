package ucan.edu.api_sig_invest_angola.dtos.auth;

import jakarta.validation.constraints.NotBlank;

public record ResetPasswordRequestDTO(
        @NotBlank(message = "A password não pode ser nula") String password,
        @NotBlank(message = "A password não pode ser nula") String confirmPassword) {
}
