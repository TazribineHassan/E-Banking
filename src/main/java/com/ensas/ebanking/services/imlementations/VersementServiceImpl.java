package com.ensas.ebanking.services.imlementations;

import com.ensas.ebanking.entities.Agent;
import com.ensas.ebanking.entities.Client;
import com.ensas.ebanking.entities.Compte;
import com.ensas.ebanking.entities.Versement;
import com.ensas.ebanking.exceptions.domain.AccountNotFoundException;
import com.ensas.ebanking.exceptions.domain.EmailExistException;
import com.ensas.ebanking.exceptions.domain.UserExistExistException;
import com.ensas.ebanking.exceptions.domain.UserNotFoundException;
import com.ensas.ebanking.repositories.VersementRepository;
import com.ensas.ebanking.services.AgentService;
import com.ensas.ebanking.services.ClientService;
import com.ensas.ebanking.services.CompteService;
import com.ensas.ebanking.services.VersementService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

import static com.ensas.ebanking.constant.TransactionConstants.TYPE_VERSEMENT;


@Service
@Transactional
public class VersementServiceImpl implements VersementService {

    private final VersementRepository versementRepository;
    private final CompteService compteService;
    private final ClientService clientService;
    private final AgentService agentService;

    public VersementServiceImpl(VersementRepository versementRepository, CompteService compteService, ClientService clientService, AgentService agentService) {
        this.versementRepository = versementRepository;
        this.compteService = compteService;
        this.clientService = clientService;
        this.agentService = agentService;
    }


    @Override
    public Versement addVersement(String Nom_verseur,
                                  String CIN_verseur,
                                  double Montant_versement,
                                  String num_compte_beneficiaire,
                                  Agent currentAgent) throws AccountNotFoundException, UserNotFoundException, UserExistExistException, EmailExistException {

        //find the destination account
        Compte distCompte = compteService.findComptByNum(num_compte_beneficiaire);
        if (distCompte == null)
            throw new AccountNotFoundException();

        // find current client
        Client client = distCompte.getClient();

        Versement versement = new Versement();
        versement.setNom_verseur(Nom_verseur);
        versement.setCin_verseur(CIN_verseur);
        versement.setNum_compte(distCompte.getNumCompte());
        versement.setMontant(Montant_versement);
        versement.setDate_transaction(LocalDate.now());
        versement.setType_transaction(TYPE_VERSEMENT);

        // attach the transaction to client and agent
        versement.setAgent(currentAgent);
        versement.setClient(client);
        Versement addedVersement = versementRepository.save(versement);

        // add money to the destination account
        distCompte.setSolde(distCompte.getSolde() + Montant_versement);
        distCompte = compteService.updateCompte(distCompte);

        return addedVersement;
    }
}
