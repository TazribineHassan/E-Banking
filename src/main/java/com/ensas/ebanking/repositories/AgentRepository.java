package com.ensas.ebanking.repositories;

import com.ensas.ebanking.entities.Agence;
import com.ensas.ebanking.entities.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgentRepository extends JpaRepository<Agent,Long> {
    Agent findByAgence(Agence agence);

    List<Agent> findByIsActive(boolean etat);
}
