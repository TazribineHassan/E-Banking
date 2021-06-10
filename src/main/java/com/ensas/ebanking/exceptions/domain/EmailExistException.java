package com.ensas.ebanking.exceptions.domain;

public class EmailExistException extends Exception{

    public EmailExistException(String message) {
        super(message);
    }
}
