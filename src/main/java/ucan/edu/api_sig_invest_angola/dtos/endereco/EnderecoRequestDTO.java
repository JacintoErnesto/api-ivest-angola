package ucan.edu.api_sig_invest_angola.dtos.endereco;

import jakarta.validation.constraints.NotBlank;

public record EnderecoRequestDTO(
        @NotBlank(message = "O campo nome do bairro é obrigatório")
        String nomeBairro,
        @NotBlank(message = " O campo nome da rua é obrigatório")
        String nomeRua,
        String numeroCasa,
        @NotBlank(message = " O campo localidade é obrigatório")
        String localidade
) {
}
