package ucan.edu.api_sig_invest_angola.dtos.endereco;

import java.time.LocalDateTime;

public record EnderecoReturnDTO(Long id,String nomeBairro, String nomeRua, String numeroCasa,
                                String localidade, LocalDateTime dataRegistro) {
}
