package com.ensas.ebanking.resource;


import com.ensas.ebanking.domains.UserPrincipal;
import com.ensas.ebanking.entities.Client;
import com.ensas.ebanking.entities.Compte;
import com.ensas.ebanking.exceptions.domain.EmailExistException;
import com.ensas.ebanking.exceptions.domain.UserExistExistException;
import com.ensas.ebanking.exceptions.domain.UserNotFoundException;
import com.ensas.ebanking.services.CompteService;
import com.ensas.ebanking.services.imlementations.ClientServiceImpl;
import com.ensas.ebanking.utilities.JWTokenProvider;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import java.util.Date;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Random;

import static com.ensas.ebanking.constant.SecurityConstant.JWT_TOKEN_HEADER;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path= {"/agent"})
@PreAuthorize("hasAnyAuthority('manage_clients')")
public class AgentResource {

    private final ClientServiceImpl clientService;
    private final AuthenticationManager authenticationManager;
    private final JWTokenProvider jwTokenProvider;
    private final CompteService compteService;

    @Autowired
    public AgentResource(ClientServiceImpl clientService, AuthenticationManager authenticationManager, JWTokenProvider jwTokenProvider, CompteService compteService) {
        this.clientService = clientService;
        this.authenticationManager = authenticationManager;
        this.jwTokenProvider = jwTokenProvider;
        this.compteService = compteService;
    }

    @GetMapping("/client/all")
    public ResponseEntity<List<Client>> test(){
        return new ResponseEntity<List<Client>>(this.clientService.getClients(), OK);
    }

    @PostMapping("/client/add")
    public ResponseEntity<Client> addClient(@RequestParam String cin,
                                            @RequestParam String nom,
                                            @RequestParam String prenom,
                                            @RequestParam String email,
                                            @RequestParam String id_agence ) throws UserNotFoundException, UserExistExistException, EmailExistException, MessagingException {

        //add the client to database
        Client addedClient = this.clientService.addClient(cin, nom, prenom, email, id_agence);

        // create new bank account for the new client
        Compte compte = new Compte();
        compte.setNum_compte(RandomStringUtils.randomNumeric(14));
        compte.setClient(addedClient);
        compte.setSolde(0);
        Compte addedCompte = this.compteService.addCompte(compte);
        addedClient.setCompte(addedCompte);
        this.clientService.updateClient(addedClient);

        return new ResponseEntity<>(addedClient, OK);
    }

    @PutMapping("/client/update")
    public ResponseEntity<Client> updateClient(@RequestParam int id,
                                               @RequestParam String cin,
                                               @RequestParam String nom,
                                               @RequestParam String prenom,
                                               @RequestParam String email,
                                               @RequestParam String type_client,
                                               @RequestParam String num_tele,
                                               @RequestParam Date date_naissance,
                                               @RequestParam Date lastLoginDate,
                                               @RequestParam Date lastLoginDateDisplay,
                                               @RequestParam Date joinDate,
                                               @RequestParam String username,
                                               @RequestParam boolean isActive ,
                                               @RequestParam boolean isNotLocked ){
        Client client = clientService.getClientByID(id);
        client.setCin(cin);
        client.setEmail(email);
        client.setPrenom(prenom);
        client.setNom(nom);
        client.setType_client(type_client);
        client.setUsername(username);
        client.setNotLocked(isNotLocked);
        client.setActive(isActive);
        client.setJoinDate(joinDate);
        client.setDate_naissance(date_naissance);
        client.setLastLoginDate(lastLoginDate);
        client.setLastLoginDateDisplay(lastLoginDateDisplay);
        client.setNum_tele(num_tele);

        Client updatedClient = this.clientService.updateClient(client);
        return new ResponseEntity<>(updatedClient, OK);
    }

    @PutMapping("/client/terminate/{id}")
    public ResponseEntity<Client> terminateClient(@PathVariable int id){
        Client terminatedClient = this.clientService.terminateClient(id);
        return new ResponseEntity<>(terminatedClient, OK);
    }

    private void authentication(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    private HttpHeaders getJwtHeader(UserPrincipal userPrincipal) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwTokenProvider.generateJwtToken(userPrincipal));
        return headers;
    }
}
