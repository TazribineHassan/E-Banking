package com.ensas.ebanking.resource;


import com.ensas.ebanking.domains.UserPrincipal;
import com.ensas.ebanking.entities.*;
import com.ensas.ebanking.exceptions.domain.AccountNotFoundException;
import com.ensas.ebanking.exceptions.domain.EmailExistException;
import com.ensas.ebanking.exceptions.domain.UserExistExistException;
import com.ensas.ebanking.exceptions.domain.UserNotFoundException;
import com.ensas.ebanking.services.AgentService;
import com.ensas.ebanking.services.CompteService;
import com.ensas.ebanking.services.VersementService;
import com.ensas.ebanking.services.imlementations.ClientServiceImpl;
import com.ensas.ebanking.utilities.JWTokenProvider;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Locale;

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
    private final AgentService agentService;
    private final VersementService versementService;


    @Autowired
    public AgentResource(ClientServiceImpl clientService,
                         AuthenticationManager authenticationManager,
                         JWTokenProvider jwTokenProvider,
                         CompteService compteService,
                         AgentService agentService,
                         VersementService versementService) {
        this.clientService = clientService;
        this.authenticationManager = authenticationManager;
        this.jwTokenProvider = jwTokenProvider;
        this.compteService = compteService;
        this.agentService = agentService;
        this.versementService = versementService;
    }

    /*
    *           TRANSACTION MANAGEMENT
    * */
    @GetMapping("/transaction/all")
    public ResponseEntity<List<Transaction>> getAllTransactions(Principal principal){

        String auth_username = principal.getName();
        Agent currentAgent = agentService.findUserByUsername(auth_username);
        List<Transaction> transactions = new ArrayList<>(currentAgent.getTransactions());
        return new ResponseEntity<>(transactions, OK);
    }

    @PostMapping("/transaction/make")
    public ResponseEntity<Transaction> makeVersement(Principal principal,
                                               @RequestParam(name = "nom_verseur") String nom_verseur,
                                               @RequestParam(name = "CIN_verseur") String CIN_verseur,
                                               @RequestParam(name = "num_compte_beneficiaire") String num_compte_beneficiaire,
                                               @RequestParam(name = "Montant_versement") double Montant_versement ) throws UserNotFoundException, UserExistExistException, EmailExistException, AccountNotFoundException {

        String auth_username = principal.getName();
        Agent currentAgent = agentService.findUserByUsername(auth_username);
        if (currentAgent == null)
            throw  new UserNotFoundException("agent couldn't be found");

        Transaction transaction = versementService.addVersement(nom_verseur,
                                                                CIN_verseur,
                                                                Montant_versement,
                                                                num_compte_beneficiaire,
                                                                currentAgent);

        return new ResponseEntity<>(transaction, OK);
    }


    /*
     *           CLIENTS MANAGEMENT
     * */
    @GetMapping("/client/all")
    public ResponseEntity<List<Client>> getAllClients(Principal principal){
        String auth_username = principal.getName();
        Agent currentAgent = agentService.findUserByUsername(auth_username);
        List<Client> clients = new ArrayList<Client>(currentAgent.getAgence().getClients());
        return new ResponseEntity<List<Client>>(clients, OK);
    }

    @PostMapping("/client/add")
    public ResponseEntity<Client> addClient(@RequestParam(name = "cin") String cin,
                                            @RequestParam(name = "nom") String nom,
                                            @RequestParam(name = "prenom") String prenom,
                                            @RequestParam(name = "email") String email,
                                            @RequestParam(name = "num_tele") String num_tele,
                                            @RequestParam(name = "date_naissance") String date_naissance,
                                            @RequestParam(name = "id_agence") Long id_agence ) throws UserNotFoundException, UserExistExistException, EmailExistException, MessagingException {

        //add the client to database
        Client addedClient = this.clientService.addClient(cin, nom, prenom, email, num_tele, new Date(date_naissance), id_agence);

        // create new bank account for the new client
        Compte compte = new Compte();
        compte.setNumCompte(RandomStringUtils.randomNumeric(14));
        compte.setClient(addedClient);
        compte.setSolde(0);
        Compte addedCompte = this.compteService.addCompte(compte);
        addedClient.setCompte(addedCompte);
        this.clientService.updateClient(StringUtils.EMPTY, addedClient);

        return new ResponseEntity<>(addedClient, OK);
    }

    @PutMapping("/client/update")
    public ResponseEntity<Client> updateClient(@RequestParam(name = "id") Long id,
                                               @RequestParam(name = "cin") String cin,
                                               @RequestParam(name = "nom") String nom,
                                               @RequestParam(name = "prenom") String prenom,
                                               @RequestParam(name = "email") String email,
                                               @RequestParam(name = "type_client") String type_client,
                                               @RequestParam(name = "num_tele") String num_tele,
                                               @RequestParam(name = "date_naissance") String date_naissance,
                                               @RequestParam(name = "lastLoginDate") String lastLoginDate,
                                               @RequestParam(name = "lastLoginDateDisplay") String lastLoginDateDisplay,
                                               @RequestParam(name = "joinDate") String joinDate,
                                               @RequestParam(name = "username") String username,
                                               @RequestParam(name = "isActive") String isActive ,
                                               @RequestParam(name = "isNotLocked") String isNotLocked ) throws UserNotFoundException, UserExistExistException, EmailExistException, ParseException {

        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

        Client client = clientService.getClientByID(id);
        String current_username = client.getUsername();
        client.setCin(cin);
        client.setEmail(email);
        client.setPrenom(prenom);
        client.setNom(nom);
        client.setType_client(type_client);
        client.setUsername(username);
        client.setNotLocked(Boolean.parseBoolean(isNotLocked));
        client.setActive(Boolean.parseBoolean(isActive));
        client.setJoinDate(format.parse(joinDate));
        client.setDate_naissance(format.parse(date_naissance));
        client.setLastLoginDate(format.parse(lastLoginDate));
        client.setLastLoginDateDisplay(format.parse(lastLoginDateDisplay));
        client.setNum_tele(num_tele);

        Client updatedClient = this.clientService.updateClient(current_username, client);
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
