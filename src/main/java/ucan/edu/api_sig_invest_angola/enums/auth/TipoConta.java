package ucan.edu.api_sig_invest_angola.enums.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import ucan.edu.api_sig_invest_angola.exceptions.PortalBusinessException;

@Getter
public enum TipoConta {
    ADMINISTRADOR("Administrador"),
    EMPREENDEDOR("Empreendedor"),
    INVESTIDOR("Investidor");

    private final String descricaoTipoConta;

    TipoConta(String descricaoTipoConta) {
        this.descricaoTipoConta = descricaoTipoConta;
    }

    @JsonValue
    public String toValue() {
        return name();
    }
    public static TipoConta forDescricao(String descricao) {
        for (TipoConta tipo : TipoConta.values()) {
            if (tipo.getDescricaoTipoConta().equalsIgnoreCase(descricao)) {
                return tipo;
            }
        }
        throw new PortalBusinessException("Tipo de conta inválido: " + descricao);
    }

    @JsonCreator
    public static TipoConta forValue(String v) {
        return TipoConta.forDescricao(v);
    }

}
