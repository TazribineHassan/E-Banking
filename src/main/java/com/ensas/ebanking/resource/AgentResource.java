package com.ensas.ebanking.resource;


import com.ensas.ebanking.entities.Client;
import com.ensas.ebanking.services.imlementations.ClientServiceImpl;
import com.ensas.ebanking.utilities.JWTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.HttpStatus.OK;

@RequestMapping(path= {"/agent"})
public class AgentResource {

    private ClientServiceImpl clientService;
    private AuthenticationManager authenticationManager;
    private JWTokenProvider jwTokenProvider;

    @Autowired
    public AgentResource(ClientServiceImpl clientService, AuthenticationManager authenticationManager, JWTokenProvider jwTokenProvider) {
        this.clientService = clientService;
        this.authenticationManager = authenticationManager;
        this.jwTokenProvider = jwTokenProvider;
    }

    @PostMapping("/client/add")
    @PreAuthorize("hasAnyAuthority('manage_clients')")
    public ResponseEntity<Client> addClient(@RequestBody Client client){
        Client addedClient = this.clientService.addClient(client);
        return new ResponseEntity<>(addedClient, OK);
    }

}
