package com.ensas.ebanking.services;

import com.ensas.ebanking.entities.Client;

import java.util.List;

public interface ClientService {
    public List<Client> getClients();
    public Client addClient(Client client);
    public Client updateClient(Client client);
    public Client terminateClient(int client_id);
}
