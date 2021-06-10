package com.ensas.ebanking.services;

import com.ensas.ebanking.entities.Banque;

public interface BanqueService {
    public Banque addToSolde(double amount);
    public Banque substractFromSolde(double amount);
}
