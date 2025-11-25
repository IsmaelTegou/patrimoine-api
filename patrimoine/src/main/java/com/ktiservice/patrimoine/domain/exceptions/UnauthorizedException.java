package com.ktiservice.patrimoine.domain.exceptions;

/**
 * Exception thrown when a user is not authorized to perform an operation.
 */
public class UnauthorizedException extends DomainException {

    public UnauthorizedException(String message) {
        super(message, "UNAUTHORIZED", 403);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, "UNAUTHORIZED", 403, cause);
    }
}