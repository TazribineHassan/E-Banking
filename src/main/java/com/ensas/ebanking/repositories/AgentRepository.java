package com.ensas.ebanking.repositories;

import com.ensas.ebanking.entities.Agent;

import javax.transaction.Transactional;

@Transactional
public interface AgentRepository extends UserBaseRepository<Agent> {

}
