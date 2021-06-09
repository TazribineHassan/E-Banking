package com.ensas.ebanking.services;

import com.ensas.ebanking.entities.Agent;
import com.ensas.ebanking.exceptions.domain.EmailExistException;
import com.ensas.ebanking.exceptions.domain.UserExistExistException;
import com.ensas.ebanking.exceptions.domain.UserNotFoundException;

public interface AgentService {
    public Agent addAgent(Agent agent);
    public Agent findUserByUsername(String username);

    Agent findAgentByID(int id);

    Agent updateAgent(String current_username, Agent agent) throws UserNotFoundException, UserExistExistException, EmailExistException;
}
