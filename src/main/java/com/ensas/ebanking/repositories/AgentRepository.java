package com.ensas.ebanking.repositories;

import com.ensas.ebanking.entities.Agent;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
public interface AgentRepository extends UserBaseRepository<Agent> {
}
