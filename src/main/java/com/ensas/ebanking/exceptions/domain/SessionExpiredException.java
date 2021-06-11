package com.ensas.ebanking.exceptions.domain;

import static com.ensas.ebanking.constant.TransactionConstants.SESSION_EXPIRED_MESSAGE;

public class SessionExpiredException extends Exception{
    public SessionExpiredException(){ super(SESSION_EXPIRED_MESSAGE);}
    public SessionExpiredException(String message){ super(message);}
}
