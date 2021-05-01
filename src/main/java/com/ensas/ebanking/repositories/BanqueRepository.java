package com.ensas.ebanking.repositories;

import com.ensas.ebanking.entities.Adresse;
import com.ensas.ebanking.entities.Banque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BanqueRepository extends JpaRepository<Banque,Integer> {
}
