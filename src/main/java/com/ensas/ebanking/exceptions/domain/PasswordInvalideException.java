package com.ensas.ebanking.exceptions.domain;

import static com.ensas.ebanking.constant.TransactionConstants.PASSWORD_NOT_VALIDE_MESSAGE;

public class PasswordInvalideException extends Exception{
    public PasswordInvalideException(){ super(PASSWORD_NOT_VALIDE_MESSAGE); }
    public PasswordInvalideException(String msg){ super(msg); }
}
