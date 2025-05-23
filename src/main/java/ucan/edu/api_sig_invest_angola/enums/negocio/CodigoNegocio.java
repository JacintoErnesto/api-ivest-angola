package ucan.edu.api_sig_invest_angola.enums.negocio;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import ucan.edu.api_sig_invest_angola.enums.auth.TipoConta;

@Getter
public enum CodigoNegocio {
    PLANO_NEGOCIO("Plano de Negócio"),
    ANALISE_INVESTIMENTO("Análise de Investimento"),
    ;
    private final String descricao;

    CodigoNegocio(String descricao) {
        this.descricao = descricao;
    }

    @JsonValue
    public String toValue() {
        return name();
    }

    public static CodigoNegocio fromDescricao(String descricao) {
        for (CodigoNegocio codigo : values()) {
            if (codigo.getDescricao().equalsIgnoreCase(descricao)) {
                return codigo;
            }
        }
        return null;
    }
    @JsonCreator
    public static CodigoNegocio forValue(String v) {
        return CodigoNegocio.fromDescricao(v);
    }
}

