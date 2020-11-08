package com.exceptions;

public class InsufficientPaymentException extends RuntimeException {
    private final String message;
    private final long remaining;

    public InsufficientPaymentException(String message, long remaining) {
        this.message = message;
        this.remaining = remaining;
    }

    public long getRemaining(){
        return remaining;
    }

    @Override
    public String getMessage(){
        return message + remaining;
    }
}
