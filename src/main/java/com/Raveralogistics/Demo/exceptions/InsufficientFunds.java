package com.Raveralogistics.Demo.exceptions;

public class InsufficientFunds extends RuntimeException {
    public InsufficientFunds(String message) {
        super(message);
    }
}
