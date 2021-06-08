package com.ensas.ebanking.services.imlementations;


import com.ensas.ebanking.entities.Client;
import com.ensas.ebanking.repositories.AgenceRepository;
import com.ensas.ebanking.repositories.ClientRepository;
import com.ensas.ebanking.services.ClientService;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

import static com.ensas.ebanking.enumeration.Role.ROLE_CLIENT;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;
    private PasswordEncoder passwordEncoder;
    private AgenceRepository agenceRepository;
    private org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());


    public ClientServiceImpl(ClientRepository clientRepository, PasswordEncoder passwordEncoder, AgenceRepository agenceRepository) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
        this.agenceRepository = agenceRepository;
    }
    @Override
    public List<Client> getClients() {
        return (List<Client>) clientRepository.findAll();
    }

    @Override
    public Client addClient(Client client){

        String password = generatePassword();
        logger.info("the new client got the password: " + password);
        client.setJoinDate(new Date());
        client.setType_client("individuel");
        client.setActive(true);
        client.setNotLocked(true);
        client.setPassword(encodePassword(password));
        client.setRoles(ROLE_CLIENT.name());
        client.setAuthorities(ROLE_CLIENT.getAuthorities());
        return this.clientRepository.save(client);
    }

    @Override
    public Client updateClient(Client client) {
        return this.clientRepository.save(client);
    }

    @Override
    public Client terminateClient(int client_id) {
        Client client = clientRepository.findById(client_id).get();
        client.setActive(false);
        client.setNotLocked(false);
        return this.clientRepository.save(client);
    }

    private String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(8);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

}
