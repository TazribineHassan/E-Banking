package com.ensas.ebanking.services;

import com.ensas.ebanking.entities.Client;
import com.ensas.ebanking.exceptions.domain.EmailExistException;
import com.ensas.ebanking.exceptions.domain.UserExistExistException;
import com.ensas.ebanking.exceptions.domain.UserNotFoundException;

import javax.mail.MessagingException;
import java.util.List;

public interface ClientService {
    public List<Client> getClients();
    public Client addClient(String cin,
                            String nom,
                            String prenom,
                            String email,
                            String id_agence) throws UserNotFoundException, UserExistExistException, EmailExistException, MessagingException;
    public Client updateClient(Client client);
    public Client terminateClient(int client_id);
    public Client getClientByID(int id);
}
