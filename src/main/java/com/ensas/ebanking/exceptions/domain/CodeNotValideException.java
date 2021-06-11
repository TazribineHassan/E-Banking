package com.ensas.ebanking.exceptions.domain;

import static com.ensas.ebanking.constant.TransactionConstants.CODE_NOT_VALIDE_MESSAGE;

public class CodeNotValideException extends Exception{
    public CodeNotValideException() { super(CODE_NOT_VALIDE_MESSAGE);}
    public CodeNotValideException(String message) { super(message);}
}
