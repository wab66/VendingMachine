package com.exceptions;

public class InsufficientChangeException extends RuntimeException {
    private final String message;

    public InsufficientChangeException(String string) {
        this.message = string;
    }

    @Override
    public String getMessage(){
        return message;
    }

}
