package com.ensas.ebanking.resource;


import com.ensas.ebanking.entities.Client;
import com.ensas.ebanking.services.UserService;
import com.ensas.ebanking.utilities.JWTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path= {"/agent"})
public class AgentResource {

    private UserService userService;
    private AuthenticationManager authenticationManager;
    private JWTokenProvider jwTokenProvider;

    @Autowired
    public AgentResource(UserService userService, AuthenticationManager authenticationManager, JWTokenProvider jwTokenProvider) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwTokenProvider = jwTokenProvider;
    }


    public ResponseEntity<Client> addClient(){

        return null;
    }

}
