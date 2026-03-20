package org.example.backend.exception;

public class NoLoanException extends RuntimeException {
    public NoLoanException(String message) {
        super(message);
    }
}
