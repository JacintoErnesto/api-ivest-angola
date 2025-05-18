package ucan.edu.api_sig_invest_angola.dtos.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
public class RestMessageReturnDTO implements Serializable {
	/**
	 * 
	 */
	@Serial
    private static final long serialVersionUID = 1369445898469071763L;
    private int codigo;
    private String mensagem;

    public RestMessageReturnDTO() {
        super();
    }

    public RestMessageReturnDTO(int cod, String message) {
        this();
        this.codigo = cod;
        this.mensagem = message;
    }

    @Override
    public String toString() {
        return "Cod: " + this.codigo + ", Message: " + this.mensagem;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + codigo;
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
        RestMessageReturnDTO other = (RestMessageReturnDTO) obj;
        if (codigo != other.codigo)
            return false;
        return true;
    }

}
