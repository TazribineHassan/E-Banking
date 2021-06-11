package com.ensas.ebanking.resource;

import com.ensas.ebanking.constant.Factures;
import com.ensas.ebanking.entities.*;
import com.ensas.ebanking.exceptions.domain.*;
import com.ensas.ebanking.repositories.CodeVereficationRepo;
import com.ensas.ebanking.services.ClientService;
import com.ensas.ebanking.services.EmailService;
import com.ensas.ebanking.services.PayementService;
import com.ensas.ebanking.services.VirementService;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path= {"/client"})
@PreAuthorize("hasAnyAuthority('manage_account, transfer')")
public class ClinetResource {

    private final VirementService virementService;
    private final ClientService clientService;
    private final EmailService emailService;
    private final CodeVereficationRepo codeVereficationRepo;
    public final PayementService payementService;

    @Autowired
    public ClinetResource(VirementService virementService, ClientService clientService, EmailService emailService, CodeVereficationRepo codeVereficationRepo, PayementService payementService) {
        this.virementService = virementService;
        this.clientService = clientService;
        this.emailService = emailService;
        this.codeVereficationRepo = codeVereficationRepo;
        this.payementService = payementService;
    }


    /*
    *
    *       VIREMENT
    * */

    @PostMapping("/virement/make")
    public ResponseEntity<Virement> makeVirement(@RequestParam(name = "num_compte_beneficiaire") String num_compte_beneficiaire,
                                                                   @RequestParam(name = "Montant_virement") double amount,
                                                                   HttpServletRequest request,
                                                                   Principal principal) throws MessagingException {
        // get the current client
        String username = principal.getName();
        Client currentClient = clientService.findClientByUsername(username);

        //build the transaction
        Virement virement = new Virement();
        virement.setNum_compte_source(currentClient.getCompte().getNumCompte());
        virement.setNum_compte_beneficiaire(num_compte_beneficiaire);
        virement.setMontant(amount);


        // generate verification code
        String verificationCode = generateVerificationCode();

        // send verification mail to the beneficial client
//        emailService.sendConfirmationEmail(currentClient.getNom() + " " + currentClient.getPrenom(),
//                                           verificationCode,
//                                           currentClient.getEmail());

        // add verification code and transaction to the database
        CodeVerefication codeVerefication = codeVereficationRepo.findCodeVereficationByUsername(username);
        if (codeVerefication == null)
            codeVerefication = new CodeVerefication();
        codeVerefication.setNum_compte_source(currentClient.getCompte().getNumCompte());
        codeVerefication.setNum_compte_beneficiaire(num_compte_beneficiaire);
        codeVerefication.setMontant_virement(amount);
        codeVerefication.setCode(verificationCode);
        codeVerefication.setUsername(username);
        codeVerefication.setExpirationDate(new Date(System.currentTimeMillis() + 30_000));
        codeVereficationRepo.save(codeVerefication);


        System.out.println("verification code = " + verificationCode);
        return new ResponseEntity<>(virement, OK);
    }

    @PostMapping("/virement/verify/{code}")
    public ResponseEntity<Virement> validateVirement(@PathVariable String code,
                                                     HttpServletRequest request,
                                                     Principal principal)
                                                        throws SessionExpiredException, CodeNotValideException, BalanceNotEnoughException, AccountNotFoundException {

        // get the current client username
        String username = principal.getName();

        //get the verification coordinates from database
        CodeVerefication codeVerefication = codeVereficationRepo.findCodeVereficationByUsername(username);
        if (codeVerefication == null)
            throw new CodeNotValideException();

        if ( codeVerefication.getExpirationDate().toInstant().isBefore(Instant.now()) )
            throw new SessionExpiredException();

        String realCode = codeVerefication.getCode();
        if (!realCode.equals(code))
            throw new CodeNotValideException();

        Virement addedVirement = virementService.makeVirement(codeVerefication.getNum_compte_source(),
                                                              codeVerefication.getNum_compte_beneficiaire(),
                                                              codeVerefication.getMontant_virement());

        return new ResponseEntity<>(addedVirement, OK);
    }

    /*
     *
     *       Payement
     * */

    @GetMapping("/payement/check/{code_facture}")
    public ResponseEntity<Double> checkPayement(@PathVariable String code_facture) throws FactureNotFoundException {
        Double price = new Factures().get(code_facture);
        return new ResponseEntity<>(price, OK);
    }

    @PostMapping("/payement/make")
    public ResponseEntity<Payment> makePayement(@RequestParam(name = "num_facture") String num_facture,
                                                 Principal principal) throws MessagingException, FactureNotFoundException {
        // get the current client
        String username = principal.getName();
        Client currentClient = clientService.findClientByUsername(username);

        // get facture amount
        Double amount = new Factures().get(num_facture);

        //build the transaction
        Payment payement = new Payment();
        payement.setCode_facture(num_facture);
        payement.setMontant_facture(amount);


        // generate verification code
        String verificationCode = generateVerificationCode();

        // send verification mail to the beneficial client
//        emailService.sendConfirmationEmail(currentClient.getNom() + " " + currentClient.getPrenom(),
//                                           verificationCode,
//                                           currentClient.getEmail());

        // add verification code and transaction to the database
        CodeVerefication codeVerefication = codeVereficationRepo.findCodeVereficationByUsername(username);
        if (codeVerefication == null)
            codeVerefication = new CodeVerefication();
        codeVerefication.setNum_compte_source(currentClient.getCompte().getNumCompte());
        codeVerefication.setNum_compte_beneficiaire(num_facture);
        codeVerefication.setMontant_virement(amount);
        codeVerefication.setCode(verificationCode);
        codeVerefication.setUsername(username);
        codeVerefication.setExpirationDate(new Date(System.currentTimeMillis() + 30_000));
        codeVereficationRepo.save(codeVerefication);


        System.out.println("verification code = " + verificationCode);
        return new ResponseEntity<>(payement, OK);
    }

    @PostMapping("/payement/verify/{code}")
    public ResponseEntity<Payment> validatePayement(@PathVariable String code,
                                                     Principal principal)
            throws SessionExpiredException, CodeNotValideException, BalanceNotEnoughException, AccountNotFoundException {

        // get the current client username
        String username = principal.getName();

        //get the verification coordinates from database
        CodeVerefication codeVerefication = codeVereficationRepo.findCodeVereficationByUsername(username);
        if (codeVerefication == null)
            throw new CodeNotValideException();

        if ( codeVerefication.getExpirationDate().toInstant().isBefore(Instant.now()) )
            throw new SessionExpiredException();

        String realCode = codeVerefication.getCode();
        if (!realCode.equals(code))
            throw new CodeNotValideException();

        Payment addedPayment = payementService.makePayment(codeVerefication.getNum_compte_source(),
                codeVerefication.getNum_compte_beneficiaire(), // it's the facture num
                codeVerefication.getMontant_virement());

        return new ResponseEntity<>(addedPayment, OK);
    }

    @RequestMapping("/transaction/all")
    public List<Transaction> getAllTransactions(Principal principal){
        // get the current client
        String username = principal.getName();
        Client currentClient = clientService.findClientByUsername(username);

        return List.copyOf(currentClient.getTransactions());
    }


    private String generateVerificationCode(){
        return RandomStringUtils.randomNumeric(6);
    }

}
