package com.ensas.ebanking.services.imlementations;

import com.ensas.ebanking.entities.Compte;
import com.ensas.ebanking.repositories.CompteRepository;
import com.ensas.ebanking.services.CompteService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@Qualifier("CompteDetailsService")
public class CompteServiceImpl implements CompteService {
    private final CompteRepository compteRepository;

    public CompteServiceImpl(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }

    @Override
    public Compte addCompte(Compte compte) {
        return compteRepository.save(compte);
    }
}
