package com.ktiservice.patrimoine.exceptions;

import lombok.Getter;

/**
 * Base exception for all domain-related errors.
 * Exceptions in the domain layer represent business rule violations.
 */
@Getter
public class DomainException extends RuntimeException {

    private final String errorCode;
    private final int httpStatusCode;

    public DomainException(String message) {
        this(message, "DOMAIN_ERROR", 400);
    }

    public DomainException(String message, String errorCode, int httpStatusCode) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatusCode = httpStatusCode;
    }

    public DomainException(String message, Throwable cause) {
        this(message, "DOMAIN_ERROR", 400, cause);
    }

    public DomainException(String message, String errorCode, int httpStatusCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.httpStatusCode = httpStatusCode;
    }

}
