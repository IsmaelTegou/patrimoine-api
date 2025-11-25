package com.ktiservice.patrimoine.domain.exceptions;

/**
 * Exception thrown when an invalid operation is attempted.
 */
public class InvalidOperationException extends DomainException {

    public InvalidOperationException(String message) {
        super(message, "INVALID_OPERATION", 400);
    }

    public InvalidOperationException(String message, String errorCode) {
        super(message, errorCode, 400);
    }

    public InvalidOperationException(String message, Throwable cause) {
        super(message, "INVALID_OPERATION", 400, cause);
    }
}
