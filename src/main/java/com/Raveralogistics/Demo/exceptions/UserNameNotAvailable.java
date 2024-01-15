package com.Raveralogistics.Demo.exceptions;

public class UserNameNotAvailable extends RuntimeException{
    public UserNameNotAvailable(String message){
        super(message);
    }
}
