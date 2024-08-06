package com.service.translator.exception;
/**
 * @author user
 * @year 2024
 */

public class TranslationException extends RuntimeException {
    public TranslationException(String message) {
        super(message);
    }

    public TranslationException(String message, Throwable cause) {
        super(message, cause);
    }
}

