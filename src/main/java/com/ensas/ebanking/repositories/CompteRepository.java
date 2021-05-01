package com.ensas.ebanking.repositories;

import com.ensas.ebanking.entities.Adresse;
import com.ensas.ebanking.entities.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompteRepository extends JpaRepository<Compte,Integer> {

}
