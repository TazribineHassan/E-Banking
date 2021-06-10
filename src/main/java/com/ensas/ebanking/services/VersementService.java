package com.ensas.ebanking.services;

import com.ensas.ebanking.entities.Agent;
import com.ensas.ebanking.entities.Versement;
import com.ensas.ebanking.exceptions.domain.AccountNotFoundException;
import com.ensas.ebanking.exceptions.domain.EmailExistException;
import com.ensas.ebanking.exceptions.domain.UserExistExistException;
import com.ensas.ebanking.exceptions.domain.UserNotFoundException;

import javax.mail.MessagingException;

public interface VersementService {
    public Versement addVersement(String Nom_versement,
                                  String CIN_verseur,
                                  double Montant_versement,
                                  String num_compte_beneficiaire, Agent currentAgent) throws AccountNotFoundException, UserNotFoundException, UserExistExistException, EmailExistException, MessagingException;
}
