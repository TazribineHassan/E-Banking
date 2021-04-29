package com.ensas.ebanking.controllers;



import com.ensas.ebanking.entities.Agent;
import com.ensas.ebanking.entities.Client;
import com.ensas.ebanking.entities.Payment;
import com.ensas.ebanking.entities.User;
import com.ensas.ebanking.repositories.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class TestController {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    public TestController(ClientRepository clientRepository, AgentRepository agentRepository) {
        this.clientRepository = clientRepository;
        this.agentRepository = agentRepository;
    }

    @GetMapping("/welcome")
    public String welcome(){
        return "welcome";
    }
    @GetMapping("/add")
    public String addClient(){
        //agentRepository.save(new Agent("agent_2", "agent_2", "Machrou", "Wafae","e@f.com", "0655428880"));
        return "client added";
    }

    @GetMapping("/getAgents")
    public List<Agent> getAllAgents(){
        return (List<Agent>) agentRepository.findAll();
    }

    @GetMapping("/getClients")
    public List<Client> getAllClients(){
        return (List<Client>) clientRepository.findAll();
    }

    @GetMapping("/getUsers")
    public List<User> getUsers(){
        return (List<User>) userRepository.findAll();
    }

    @GetMapping("/addTransaction")
    public String addTransaction(){
        paymentRepository.save(new Payment("Payment", LocalDate.now(),"DE 00 12 54", 2000 ));
        return "transaction added";
    }

}
