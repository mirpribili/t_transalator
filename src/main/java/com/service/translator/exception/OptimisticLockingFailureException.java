package com.service.translator.exception;
/**
 * @author user
 * @year 2024
 */
public class OptimisticLockingFailureException extends RuntimeException {
    public OptimisticLockingFailureException(String message) {
        super(message);
    }

    public OptimisticLockingFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}


