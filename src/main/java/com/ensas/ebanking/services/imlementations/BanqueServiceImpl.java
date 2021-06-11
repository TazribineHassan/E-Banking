package com.ensas.ebanking.services.imlementations;

import com.ensas.ebanking.entities.Banque;
import com.ensas.ebanking.repositories.BanqueRepository;
import com.ensas.ebanking.services.BanqueService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class BanqueServiceImpl implements BanqueService {

    private final BanqueRepository banqueRepository;

    public BanqueServiceImpl(BanqueRepository banqueRepository) {
        this.banqueRepository = banqueRepository;
    }

    @Override
    public Banque addToSolde(double amount) {
        Banque banque = banqueRepository.findAll().get(0);
        double new_solde = banque.getSolde() + amount;
        banque.setSolde(new_solde);
        return banqueRepository.save(banque);
    }

    @Override
    public Banque substractFromSolde(double amount) {
        Banque banque = banqueRepository.findAll().get(0);
        double new_solde = banque.getSolde() - amount;
        banque.setSolde(new_solde);
        return banqueRepository.save(banque);
    }
}
