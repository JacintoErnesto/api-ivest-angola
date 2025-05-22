package ucan.edu.api_sig_invest_angola.dtos.auth;

import lombok.Data;
import ucan.edu.api_sig_invest_angola.dtos.response.RestMessageReturnDTO;
import ucan.edu.api_sig_invest_angola.models.auth.Conta;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data

public class AuthReturnDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 6612646783971974274L;

    private Object data;
    private int quantidadeTotalItens;
    private RestMessageReturnDTO retorno;
    public AuthReturnDTO() {
        super();
    }

    public AuthReturnDTO(RestMessageReturnDTO retorno) {
        this();
        this.retorno = retorno;
    }

    public AuthReturnDTO(int codigoRetorno, String mensagemRetorno) {
        this();
        this.retorno = new RestMessageReturnDTO(codigoRetorno, mensagemRetorno);
    }


    public AuthReturnDTO(Object data, int quantidadeTotalItens, int codigoRetorno, String mensagemRetorno) {
        this();
        this.data = data;
        this.quantidadeTotalItens = quantidadeTotalItens;
        this.retorno = new RestMessageReturnDTO(codigoRetorno, mensagemRetorno);
    }

    public AuthReturnDTO(List<Conta> contas, int size, RestMessageReturnDTO restMessageReturnDTO) {
    }
}
