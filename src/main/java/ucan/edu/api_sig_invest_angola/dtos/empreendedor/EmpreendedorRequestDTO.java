package ucan.edu.api_sig_invest_angola.dtos.empreendedor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ucan.edu.api_sig_invest_angola.dtos.endereco.EnderecoRequestDTO;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmpreendedorRequestDTO {
        public EmpreendedorRequestDTO(String nomeEmpreendedor, String nifEmpreendedor) {
                this.nomeEmpreendedor = nomeEmpreendedor;
                this.nifEmpreendedor = nifEmpreendedor;
        }
        private String nomeEmpreendedor;
        private String nifEmpreendedor;
        private String telefoneEmpreendedor;
        private String emailEmpreendedor;
        private EnderecoRequestDTO endereco;
}
