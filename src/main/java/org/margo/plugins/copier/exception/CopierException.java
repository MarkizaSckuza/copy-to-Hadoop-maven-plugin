package org.margo.plugins.copier.exception;

public class CopierException extends Exception {
    public CopierException() {
    }

    public CopierException(String message) {
        super(message);
    }

    public CopierException(String message, Throwable cause) {
        super(message, cause);
    }

    public CopierException(Throwable cause) {
        super(cause);
    }

    public CopierException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
