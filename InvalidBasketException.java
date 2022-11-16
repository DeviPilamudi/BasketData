package com.rbc.shopping.exception;

public class InvalidBasketException extends RuntimeException {
    public InvalidBasketException(String message) {
        super(message);
    }
}