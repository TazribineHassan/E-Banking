package com.ensas.ebanking.services.imlementations;

import com.ensas.ebanking.entities.Client;
import com.ensas.ebanking.entities.Compte;
import com.ensas.ebanking.entities.Virement;
import com.ensas.ebanking.exceptions.domain.AccountNotFoundException;
import com.ensas.ebanking.exceptions.domain.BalanceNotEnoughException;
import com.ensas.ebanking.repositories.VirementRepository;
import com.ensas.ebanking.services.BanqueService;
import com.ensas.ebanking.services.CompteService;
import com.ensas.ebanking.services.EmailService;
import com.ensas.ebanking.services.VirementService;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.time.LocalDate;

import static com.ensas.ebanking.constant.TransactionConstants.TYPE_VIREMENT;

@Service
@Transactional
public class VirementServiceImpl implements VirementService {

    private final VirementRepository virementRepository;
    private final CompteService compteService;
    private final EmailService emailService;

    public VirementServiceImpl(VirementRepository virementRepository, CompteService compteService, EmailService emailService) {
        this.virementRepository = virementRepository;
        this.compteService = compteService;
        this.emailService = emailService;
    }

    @Override
    public Virement makeVirement(String num_compte_source, String num_compte_beneficiaire, double amount) throws AccountNotFoundException, BalanceNotEnoughException, MessagingException {

        //find the source account
        Compte sourceCompte = compteService.findComptByNum(num_compte_source);
        if (sourceCompte.getSolde() < amount)
            throw new BalanceNotEnoughException();

        //find the destination account
        Compte distCompte = compteService.findComptByNum(num_compte_beneficiaire);
        if (distCompte == null)
            throw new AccountNotFoundException();

        // find source and beneficial client
        Client sourceClient = sourceCompte.getClient();
        Client beneficial_client = distCompte.getClient();

        Virement virement = new Virement();
        virement.setNum_compte_source(sourceCompte.getNumCompte());
        virement.setNum_compte_beneficiaire(distCompte.getNumCompte());
        virement.setMontant(amount);
        virement.setDate_transaction(LocalDate.now());
        virement.setType_transaction(TYPE_VIREMENT);

        // attach the transaction to client
        virement.setClient(sourceClient);
        Virement addedVirement = virementRepository.save(virement);

        // substract money from source account
        double new_source_solde = sourceCompte.getSolde() - amount;
        sourceCompte.setSolde(new_source_solde);
        Compte updated_sourceCompte = compteService.updateCompte(sourceCompte);

        // add money to the destination account
        double new_dist_solde = distCompte.getSolde() + amount;
        distCompte.setSolde(new_dist_solde);
        Compte updated_distCompte = compteService.updateCompte(distCompte);

        // send email to the beneficiary
        emailService.sendFoundRecievedEmail(beneficial_client.getNom() + "" + beneficial_client.getPrenom(),
                                            virement.getMontant(),
                                            beneficial_client.getEmail());

        return addedVirement;
    }
}
