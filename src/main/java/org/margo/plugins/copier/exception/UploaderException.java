package org.margo.plugins.copier.exception;

public class UploaderException extends Exception {
    public UploaderException() {
    }

    public UploaderException(String message) {
        super(message);
    }

    public UploaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public UploaderException(Throwable cause) {
        super(cause);
    }

    public UploaderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
