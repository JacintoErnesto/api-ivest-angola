package ucan.edu.api_sig_invest_angola.enums.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

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
    public static TipoConta fromDescricao(String descricao) {
        for (TipoConta tipo : TipoConta.values()) {
            if (tipo.getDescricaoTipoConta().equalsIgnoreCase(descricao)) {
                return tipo;
            }
        }
       return null;
    }

    @JsonCreator
    public static TipoConta forValue(String v) {
        return TipoConta.fromDescricao(v);
    }

}
