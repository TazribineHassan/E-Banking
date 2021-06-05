package com.ensas.ebanking.services.imlementations;

import com.ensas.ebanking.entities.Client;
import com.ensas.ebanking.repositories.ClientRepository;
import com.ensas.ebanking.services.ClientService;

public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client addClient(Client client){
        return this.clientRepository.save(client);
    }
}
