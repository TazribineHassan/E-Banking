package com.ensas.ebanking.services.imlementations;

import com.ensas.ebanking.entities.Agent;
import com.ensas.ebanking.repositories.AgentRepository;
import com.ensas.ebanking.services.AgentService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@Qualifier("AgentDetailsService")
public class AgentServiceImpl implements AgentService {

    private final AgentRepository agentRepository;

    public AgentServiceImpl(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    @Override
    public Agent addAgent(Agent agent) {
        return agentRepository.save(agent);
    }

    @Override
    public Agent findUserByUsername(String username) {
        return (Agent) this.agentRepository.findUserByUsername(username);
    }

    @Override
    public Agent findAgentByID(int id) {
        return this.agentRepository.findById(id).get();
    }

    @Override
    public Agent updateAgent(Agent agent) {
        return this.agentRepository.save(agent);
    }
}
