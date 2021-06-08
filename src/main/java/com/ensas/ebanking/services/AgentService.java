package com.ensas.ebanking.services;

import com.ensas.ebanking.entities.Agent;

public interface AgentService {
    public Agent addAgent(Agent agent);
    public Agent findUserByUsername(String username);

    Agent findAgentByID(int id);

    Agent updateAgent(Agent agent);
}
