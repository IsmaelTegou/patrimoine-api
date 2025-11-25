package com.ktiservice.patrimoine.domain.exceptions;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Exception thrown when business validation rules are violated.
 */
@Getter
public class ValidationException extends DomainException {

    private final Map<String, String> fieldErrors;

    public ValidationException(String message) {
        super(message, "VALIDATION_ERROR", 422);
        this.fieldErrors = new HashMap<>();
    }

    public ValidationException(String message, Map<String, String> fieldErrors) {
        super(message, "VALIDATION_ERROR", 422);
        this.fieldErrors = fieldErrors != null ? fieldErrors : new HashMap<>();
    }

    public void addFieldError(String field, String message) {
        this.fieldErrors.put(field, message);
    }
}
