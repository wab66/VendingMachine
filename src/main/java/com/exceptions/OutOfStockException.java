package com.exceptions;

public class OutOfStockException extends RuntimeException {
    private final String message;

    public OutOfStockException(String string) {
        this.message = string;
    }

    @Override public String getMessage(){
        return message;
    }
}
