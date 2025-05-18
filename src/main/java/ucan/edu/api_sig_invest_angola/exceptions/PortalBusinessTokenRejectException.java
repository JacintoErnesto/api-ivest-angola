package ucan.edu.api_sig_invest_angola.exceptions;


import ucan.edu.api_sig_invest_angola.enums.response.ResponseCode;

import java.io.Serial;

public class PortalBusinessTokenRejectException extends RuntimeException {

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1L;

    public PortalBusinessTokenRejectException() {
        super();
    }

    public PortalBusinessTokenRejectException(String message) {
        super(message);
    }

    public PortalBusinessTokenRejectException(Throwable cause) {
        super(cause);
    }

    public PortalBusinessTokenRejectException(String message, Throwable cause) {
        super(message, cause);
    }

    public int getCodigo() {
        return ResponseCode.TOKEN_REJECT.getDescricao();
    }
}

