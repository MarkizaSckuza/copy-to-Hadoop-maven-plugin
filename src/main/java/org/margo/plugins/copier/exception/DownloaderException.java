package org.margo.plugins.copier.exception;

public class DownloaderException extends CopierException {
    public DownloaderException() {
    }

    public DownloaderException(String message) {
        super(message);
    }

    public DownloaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public DownloaderException(Throwable cause) {
        super(cause);
    }

    public DownloaderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
