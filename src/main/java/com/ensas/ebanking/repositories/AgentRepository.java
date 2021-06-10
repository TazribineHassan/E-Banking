package com.ensas.ebanking.repositories;

import com.ensas.ebanking.domains.User;
import com.ensas.ebanking.entities.Agence;
import com.ensas.ebanking.entities.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AgentRepository extends JpaRepository<Agent,Long> {

    User findUserByUsername(String username);

    User findUserByEmail(String email);
    Agent findByAgence(Agence agence);
    List<Agent> findByIsActive(boolean etat);
}
