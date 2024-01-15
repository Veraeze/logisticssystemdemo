package com.Raveralogistics.Demo.exceptions;

public class LoginError extends RuntimeException {
    public LoginError(String message) {
        super(message);
    }
}
