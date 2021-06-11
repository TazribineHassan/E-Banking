package com.ensas.ebanking.exceptions.domain;

import static com.ensas.ebanking.constant.TransactionConstants.FACTURE_NOT_FOUND_MESSAGE;

public class FactureNotFoundException extends Exception{
    public FactureNotFoundException(){super(FACTURE_NOT_FOUND_MESSAGE);}
    public FactureNotFoundException(String msg){super(msg);}
}
