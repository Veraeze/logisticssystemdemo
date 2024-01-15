package com.Raveralogistics.Demo.exceptions;

public class InvalidUserId extends RuntimeException {
    public InvalidUserId(String message) {
        super(message);
    }
}
