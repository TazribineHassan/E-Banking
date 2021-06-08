package com.ensas.ebanking.dbTest;

import com.ensas.ebanking.entities.Agence;
import com.ensas.ebanking.entities.Agent;
import com.ensas.ebanking.repositories.AgenceRepository;
import com.ensas.ebanking.services.AgentService;
import com.ensas.ebanking.services.ClientService;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ensas.ebanking.enumeration.Role.ROLE_AGENT;

@RestController
@RequestMapping(path= {"/test"})
public class DbTest {

    private final ClientService clientService;
    private final AgentService agentService;
    private final AgenceRepository agenceRepository;
    private final PasswordEncoder passwordEncoder;
    private Logger logger = LoggerFactory.getLogger(getClass());

    public DbTest(ClientService clientService, AgentService agentService, AgenceRepository agenceRepository, PasswordEncoder passwordEncoder) {
        this.clientService = clientService;
        this.agentService = agentService;
        this.agenceRepository = agenceRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping()
    public ResponseEntity<Object> fillDB(){
        Agence agence = new Agence();
        agence.setNom("agent 1");
        Agence newAgence = agenceRepository.save(agence);

        String username = generateUsername();
        String password = generatePassword();


        logger.info("the new agent got the username: " + username + " password: " + password);

        Agent agent = new Agent();
        agent.setUsername(username);
        agent.setPassword(encodePassword(password));
        agent.setRoles(ROLE_AGENT.name());
        agent.setAuthorities(ROLE_AGENT.getAuthorities());
        agent.setNom("agent 2");
        agent.setPrenom("agent 2");
        agent.setAgence(newAgence);
        agentService.addAgent(agent);

        return new ResponseEntity<Object>(null, HttpStatus.OK);
    }

    private String generateUsername() {
        return RandomStringUtils.randomAlphabetic(8);
    }

    private String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(8);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }


}
