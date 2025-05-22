package ucan.edu.api_sig_invest_angola.dtos.empreendedor;

import ucan.edu.api_sig_invest_angola.dtos.endereco.EnderecoReturnDTO;

import java.time.LocalDateTime;

public record EmpreendedorRetornoDTO(
        Long id,
        Long contaId,
        String nomeEmpreendedor,
        String nifEmpreendedor,
        String telefoneEmpreendedor,
        String emailEmpreendedor,
        EnderecoReturnDTO endereco,
        String anexoBilhete,
        String anexoNif,
        String anexoCartaoMunicipe,
        String anexoRegistroCriminal,
        LocalDateTime dataRegistro
) {
}
