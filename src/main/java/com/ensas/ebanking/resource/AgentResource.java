package com.ensas.ebanking.resource;


import com.ensas.ebanking.domains.HttpResponse;
import com.ensas.ebanking.domains.User;
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
import org.springframework.http.HttpStatus;
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

import javax.mail.MessagingException;
import java.util.List;
import java.util.Locale;

import static com.ensas.ebanking.constant.SecurityConstant.JWT_TOKEN_HEADER;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path= {"/agent"})
//@PreAuthorize("hasAnyAuthority('manage_clients')")
public class AgentResource {

    private static final String USER_BLOCKED_SUCCESSFULLY = "Le compte bloqué avec succès";
    private final ClientServiceImpl clientService;
    private final AuthenticationManager authenticationManager;
    private final JWTokenProvider jwTokenProvider;
    private final AgentService agentService;
    private final VersementService versementService;


    @Autowired
    public AgentResource(ClientServiceImpl clientService,
                         AuthenticationManager authenticationManager,
                         JWTokenProvider jwTokenProvider,
                         AgentService agentService,
                         VersementService versementService) {
        this.clientService = clientService;
        this.authenticationManager = authenticationManager;
        this.jwTokenProvider = jwTokenProvider;
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
                                               @RequestParam(name = "Montant_versement") double Montant_versement ) throws UserNotFoundException, UserExistExistException, EmailExistException, AccountNotFoundException, MessagingException {

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
                                            @RequestParam(name = "id_agence") Long id_agence ) throws UserNotFoundException, UserExistExistException, EmailExistException, MessagingException, ParseException {

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        //add the client to database
        Client addedClient = this.clientService.addClient(cin, nom, prenom, email, num_tele, format.parse(date_naissance), id_agence);

        //this.clientService.updateClient(StringUtils.EMPTY, addedClient);
        return new ResponseEntity<>(addedClient, OK);
    }

    @PutMapping("/client/update")
    public ResponseEntity<Client> updateClient(@RequestParam("username") String username,
                                               @RequestParam(name = "cin") String cin,
                                               @RequestParam(name = "nom") String nom,
                                               @RequestParam(name = "prenom") String prenom,
                                               @RequestParam(name = "email") String email,
                                               @RequestParam(name = "num_tele") String num_tele,
                                               @RequestParam(name = "date_naissance") String date_naissance,
                                               @RequestParam(name = "isActive") String isActive) throws UserNotFoundException, UserExistExistException, EmailExistException, ParseException {

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        Client updatedClient = clientService.updateClient(username, cin, nom, prenom, email, num_tele, format.parse(date_naissance), Boolean.parseBoolean(isActive));

        return new ResponseEntity<>(updatedClient, OK);
    }

    @PutMapping("/client/terminate/{id}")
    public ResponseEntity<HttpResponse> terminateClient(@PathVariable Long id){
        Client terminatedClient = this.clientService.terminateClient(id);
        return  response(OK, USER_BLOCKED_SUCCESSFULLY);
    }

    private void authentication(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    private HttpHeaders getJwtHeader(UserPrincipal userPrincipal) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwTokenProvider.generateJwtToken(userPrincipal));
        return headers;
    }
    private ResponseEntity<HttpResponse> response(HttpStatus status, String msg) {
        return new ResponseEntity<>(new HttpResponse(status.value(), status, status.getReasonPhrase(), msg), status);
    }
}
