package com.advisorapp.api.exception;

/**
 * For HTTP 403 errors
 */
public class UnmakableRequestException extends RuntimeException {
    public UnmakableRequestException() {
        super();
    }

    public UnmakableRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnmakableRequestException(String message) {
        super(message);
    }

    public UnmakableRequestException(Throwable cause) {
        super(cause);
    }

}
