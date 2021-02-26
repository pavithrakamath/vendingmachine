package com.sample.vendingmachine.model.exceptions;

public class InvalidOperationException extends Exception {
    public InvalidOperationException(String message) {
        super(message);
    }
}

