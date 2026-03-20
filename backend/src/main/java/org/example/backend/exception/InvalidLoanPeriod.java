package org.example.backend.exception;

public class InvalidLoanPeriod extends RuntimeException {
    public InvalidLoanPeriod(String message) {
        super(message);
    }
}
