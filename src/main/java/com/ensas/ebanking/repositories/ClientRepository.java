package com.ensas.ebanking.repositories;

import com.ensas.ebanking.entities.Agence;

import com.ensas.ebanking.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Long> {
    Client findByAgence(Agence agence);
}
