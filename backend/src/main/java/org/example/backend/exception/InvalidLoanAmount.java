package org.example.backend.exception;

public class InvalidLoanAmount extends RuntimeException {
    public InvalidLoanAmount(String message) {
        super(message);
    }
}
