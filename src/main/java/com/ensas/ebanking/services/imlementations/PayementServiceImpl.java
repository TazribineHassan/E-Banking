package com.ensas.ebanking.services.imlementations;

import com.ensas.ebanking.entities.Client;
import com.ensas.ebanking.entities.Compte;
import com.ensas.ebanking.entities.Payment;
import com.ensas.ebanking.exceptions.domain.BalanceNotEnoughException;
import com.ensas.ebanking.repositories.PayementRepository;
import com.ensas.ebanking.services.BanqueService;
import com.ensas.ebanking.services.CompteService;
import com.ensas.ebanking.services.EmailService;
import com.ensas.ebanking.services.PayementService;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.time.LocalDate;

import static com.ensas.ebanking.constant.TransactionConstants.TYPE_PAYEMENT;

@Service
@Transactional
public class PayementServiceImpl implements PayementService {

    private final PayementRepository payementRepository;
    private final CompteService compteService;
    private final EmailService emailService;
    private final BanqueService banqueService;

    public PayementServiceImpl(PayementRepository payementRepository, CompteService compteService, EmailService emailService, BanqueService banqueService) {
        this.payementRepository = payementRepository;
        this.compteService = compteService;
        this.emailService = emailService;
        this.banqueService = banqueService;
    }


    @Override
    public Payment makePayment(String num_compte_source, String numFacture, double amount) throws BalanceNotEnoughException, MessagingException {


        //find the source account and client
        Compte sourceCompte = compteService.findComptByNum(num_compte_source);
        Client sourceClient = sourceCompte.getClient();
        if (sourceCompte.getSolde() < amount)
            throw new BalanceNotEnoughException();

        Payment payement = new Payment();
        payement.setCode_facture(numFacture);
        payement.setMontant_facture(amount);
        payement.setDate_transaction(LocalDate.now());
        payement.setType_transaction(TYPE_PAYEMENT);

        // attach the transaction to client
        payement.setClient(sourceClient);
        Payment addedPayment = payementRepository.save(payement);

        // substract money from source account
        double new_source_solde = sourceCompte.getSolde() - amount;
        sourceCompte.setSolde(new_source_solde);
        Compte updated_sourceCompte = compteService.updateCompte(sourceCompte);

        // substract mony from bank
        banqueService.substractFromSolde(amount);

        // send email to the beneficiary
        emailService.sendPaymentConfirmationdEmail(sourceClient.getNom() + "" + sourceClient.getPrenom(),
                                                            payement.getCode_facture(),
                                                            payement.getMontant_facture(),
                                                            sourceClient.getEmail());

        return addedPayment;

    }
}
