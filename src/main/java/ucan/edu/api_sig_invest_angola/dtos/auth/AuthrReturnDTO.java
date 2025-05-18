package ucan.edu.api_sig_invest_angola.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ucan.edu.api_sig_invest_angola.dtos.response.RestMessageReturnDTO;

import java.io.Serial;
import java.io.Serializable;

@Data

public class AuthrReturnDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 6612646783971974274L;

    private Object data;
    private int quantidadeTotalItens;
    private RestMessageReturnDTO retorno;
    public AuthrReturnDTO() {
        super();
    }

    public AuthrReturnDTO(RestMessageReturnDTO retorno) {
        this();
        this.retorno = retorno;
    }

    public AuthrReturnDTO(int codigoRetorno, String mensagemRetorno) {
        this();
        this.retorno = new RestMessageReturnDTO(codigoRetorno, mensagemRetorno);
    }


    public AuthrReturnDTO(Object data, int quantidadeTotalItens, int codigoRetorno, String mensagemRetorno) {
        this();
        this.data = data;
        this.quantidadeTotalItens = quantidadeTotalItens;
        this.retorno = new RestMessageReturnDTO(codigoRetorno, mensagemRetorno);
    }
}
