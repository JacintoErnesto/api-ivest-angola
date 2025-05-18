package ucan.edu.api_sig_invest_angola.enums.response;

import lombok.Getter;

@Getter
public enum ResponseCode {
    SUCESSO(200), REGRA_NEGOCIO(300), NENHUM_RESGISTRO(400), ERRO_INTERNO(500), INFORMACAO(600), TOKEN_REJECT(401);

    private int descricao;

    ResponseCode(int descricao) {
        this.descricao = descricao;
    }

    public void setDescricao(int descricao) {
        this.descricao = descricao;
    }
}
