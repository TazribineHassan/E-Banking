package com.ensas.ebanking.exceptions.domain;

import static com.ensas.ebanking.constant.TransactionConstants.BALANCE_NOT_ENOUGH_MESSAGE;

public class BalanceNotEnoughException extends Exception{
    public BalanceNotEnoughException(String message) {
        super(message);
    }
    public BalanceNotEnoughException() {
        super(BALANCE_NOT_ENOUGH_MESSAGE);
    }

}

