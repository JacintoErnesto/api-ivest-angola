package ucan.edu.api_sig_invest_angola.exceptions;


import ucan.edu.api_sig_invest_angola.enums.response.ResponseCode;

import java.io.Serial;

public class PortalBusinessException extends RuntimeException {

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1L;

    public PortalBusinessException() {
        super();
    }

    public PortalBusinessException(String message) {
        super(message);
    }

    public PortalBusinessException(Throwable cause) {
        super(cause);
    }

    public PortalBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public int getCodigo() {
        return ResponseCode.REGRA_NEGOCIO.getDescricao();
    }
}

