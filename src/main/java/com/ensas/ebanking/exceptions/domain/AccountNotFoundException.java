package com.ensas.ebanking.exceptions.domain;

import static com.ensas.ebanking.constant.TransactionConstants.ACCOUNT_NOT_FOUND_MESSAGE;

public class AccountNotFoundException extends Exception{
    public AccountNotFoundException(String message) {
        super(message);
    }
    public AccountNotFoundException() {
        super(ACCOUNT_NOT_FOUND_MESSAGE);
    }

}
