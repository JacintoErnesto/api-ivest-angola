package ucan.edu.api_sig_invest_angola.exceptions;

import java.io.Serial;

public class BadRequestAlertException  extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public BadRequestAlertException() {
        super();
    }

    public BadRequestAlertException(String message) {
        super(message);
    }

    public BadRequestAlertException(String message, Throwable cause) {
        super(message, cause);
    }
}
