package com.ensas.ebanking.services;

import com.ensas.ebanking.entities.Payment;
import com.ensas.ebanking.exceptions.domain.BalanceNotEnoughException;

import javax.mail.MessagingException;

public interface PayementService {
    public Payment makePayment(String num_compte_source,String numFacture, double amount) throws BalanceNotEnoughException, MessagingException;
}
