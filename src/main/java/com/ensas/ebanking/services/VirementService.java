package com.ensas.ebanking.services;

import com.ensas.ebanking.entities.Virement;
import com.ensas.ebanking.exceptions.domain.AccountNotFoundException;
import com.ensas.ebanking.exceptions.domain.BalanceNotEnoughException;

public interface VirementService {
    public Virement makeVirement(String num_compte_source,String num_compte_beneficiaire, double amount) throws AccountNotFoundException, BalanceNotEnoughException;
}
