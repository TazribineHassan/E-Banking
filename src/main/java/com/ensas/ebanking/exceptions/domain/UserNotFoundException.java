package com.ensas.ebanking.exceptions.domain;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(String message) {
        super(message);
    }
}
