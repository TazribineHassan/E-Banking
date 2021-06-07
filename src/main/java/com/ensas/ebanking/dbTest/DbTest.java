package com.ensas.ebanking.dbTest;

import com.ensas.ebanking.entities.Agence;
import com.ensas.ebanking.entities.Agent;
import com.ensas.ebanking.repositories.AgenceRepository;
import com.ensas.ebanking.services.AgentService;
import com.ensas.ebanking.services.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path= {"/test"})
public class DbTest {

    private final ClientService clientService;
    private final AgentService agentService;
    private final AgenceRepository agenceRepository;

    public DbTest(ClientService clientService, AgentService agentService, AgenceRepository agenceRepository) {
        this.clientService = clientService;
        this.agentService = agentService;
        this.agenceRepository = agenceRepository;
    }

    @GetMapping()
    public ResponseEntity<Object> fillDB(){
        Agence agence = new Agence();
        agence.setNom("agent 1");
        Agence newAgence = agenceRepository.save(agence);

        Agent agent = new Agent();
        agent.setNom("agent 1");
        agent.setPrenom("agent 1");
        agent.setAgence(newAgence);
        agentService.addAgent(agent);

        return new ResponseEntity<Object>(null, HttpStatus.OK);
    }
}
