package ucan.edu.api_sig_invest_angola.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import ucan.edu.api_sig_invest_angola.utils.DateUtil;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Setter
@Getter
public class RestDataReturnDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 6612646783971974274L;

    private Object data;
    private int quantidadeTotalItens;
    private RestMessageReturnDTO retorno;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PaginatorDTO paginator;

    public RestDataReturnDTO() {
        super();
    }

    public RestDataReturnDTO(RestMessageReturnDTO retorno) {
        this();
        this.retorno = retorno;
    }

    public RestDataReturnDTO(int codigoRetorno, String mensagemRetorno) {
        this();
        this.retorno = new RestMessageReturnDTO(codigoRetorno, mensagemRetorno);
    }


    public RestDataReturnDTO(Object data, int quantidadeTotalItens, int codigoRetorno, String mensagemRetorno) {
        this();
        this.data = data;
        this.quantidadeTotalItens = quantidadeTotalItens;
        this.retorno = new RestMessageReturnDTO(codigoRetorno, mensagemRetorno);
    }

    @SuppressWarnings("rawtypes")
    public RestDataReturnDTO(Object data, int codigoRetorno, String mensagemRetorno) {
        this();
        this.data = data;

        if (data != null) {
            if (data instanceof Collection) {
                this.quantidadeTotalItens = ((Collection) data).size();
            }
        }
        this.retorno = new RestMessageReturnDTO(codigoRetorno, mensagemRetorno);
    }

    //PGINATOR
    public RestDataReturnDTO(Object data, PaginatorDTO paginator, int quantidadeTotalItens, int codigoRetorno, String mensagemRetorno) {
        this();
        this.data = data;
        this.quantidadeTotalItens = quantidadeTotalItens;
        this.retorno = new RestMessageReturnDTO(codigoRetorno, mensagemRetorno);
        this.paginator= paginator;
    }

    @Override
    public String toString() {
        return "RestDataReturnDTO [data=" + data + ", retorno=" + retorno + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((data == null) ? 0 : data.hashCode());
        result = prime * result + ((retorno == null) ? 0 : retorno.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RestDataReturnDTO other = (RestDataReturnDTO) obj;
        if (data == null) {
            if (other.data != null)
                return false;
        } else if (!data.equals(other.data))
            return false;
        if (retorno == null) {
            return other.retorno == null;
        } else return retorno.equals(other.retorno);
    }

    /**
     * @return the dataHora
     */
    public String getDataHora() {
        try {
            return DateUtil.formataData(new Date(), DateUtil.DIA_MES_ANO_HORA_MINUTO_SEGUNDO_TIMEZONE_PATTERN);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
