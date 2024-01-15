package com.Raveralogistics.Demo.exceptions;

public class AccountDoesNotExist extends RuntimeException {
    public AccountDoesNotExist(String message) {
        super(message);
    }
}
