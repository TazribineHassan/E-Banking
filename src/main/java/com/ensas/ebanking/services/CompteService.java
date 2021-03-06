package com.ensas.ebanking.services;

import com.ensas.ebanking.entities.Compte;

public interface CompteService {
    public Compte addCompte(Compte compte);
    public Compte findComptByNum(String num_compte);

    public Compte updateCompte(Compte compte);
}
