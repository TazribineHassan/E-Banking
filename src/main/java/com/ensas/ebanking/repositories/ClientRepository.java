package com.ensas.ebanking.repositories;

import com.ensas.ebanking.domains.User;
import com.ensas.ebanking.entities.Agence;

import com.ensas.ebanking.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client,Long> {
    User findUserByUsername(String username);

    User findUserByEmail(String email);
    Client findByAgence(Agence agence);
}
