package com.ensas.ebanking.services.imlementations;

import com.ensas.ebanking.domains.User;
import com.ensas.ebanking.entities.Agence;
import com.ensas.ebanking.entities.Agent;
import com.ensas.ebanking.entities.Client;
import com.ensas.ebanking.entities.Compte;
import com.ensas.ebanking.exceptions.domain.EmailExistException;
import com.ensas.ebanking.exceptions.domain.UserExistExistException;
import com.ensas.ebanking.exceptions.domain.UserNotFoundException;
import com.ensas.ebanking.repositories.AgenceRepository;
import com.ensas.ebanking.repositories.ClientRepository;
import com.ensas.ebanking.repositories.UserRepository;
import com.ensas.ebanking.services.ClientService;
import com.ensas.ebanking.services.EmailService;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

import static com.ensas.ebanking.constant.UserImplementationConstant.*;
import static com.ensas.ebanking.enumeration.Role.ROLE_CLIENT;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final AgenceRepository agenceRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, PasswordEncoder passwordEncoder, AgenceRepository agenceRepository, EmailService emailService, UserRepository userRepository) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
        this.agenceRepository = agenceRepository;
        this.emailService = emailService;
        this.userRepository = userRepository;
    }
    @Override
    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client addClient(String cin,
                            String nom,
                            String prenom,
                            String email,
                            String num_tele,
                            Date date_naissance,
                            Long id_agence) throws UserNotFoundException, UserExistExistException, EmailExistException, MessagingException {

        String username = generateUsername();
        String password = generatePassword();

        //validate the new client before  saving to database
        validateNewUsernameAndEmail(StringUtils.EMPTY, username, email);

        //get the current agency
        Agence agence = this.agenceRepository.findById(id_agence).get();

        // create new client
        Client client = new Client();
        // create new bank account for the new client
        Compte compte = new Compte();
        compte.setNumCompte(RandomStringUtils.randomNumeric(14));
        compte.setSolde(0);


        client.setNom(nom);
        client.setPrenom(prenom);
        client.setCin(cin);
        client.setEmail(email);
        client.setDate_naissance(date_naissance);
        client.setNum_tele(num_tele);
        client.setUsername(username);
        client.setJoinDate(new Date());
        client.setCompte(compte);
        client.setActive(true);
        client.setNotLocked(true);
        client.setPassword(encodePassword(password));
        client.setRoles(ROLE_CLIENT.name());
        client.setAuthorities(ROLE_CLIENT.getAuthorities());
        client.setAgence(agence);

        //send user and password in the email to the client
        emailService.sendNewPasswordEmail(nom + " " + prenom, username, password, email);

        //save client to database
        Client addedClient = this.clientRepository.save(client);

        //add client to agence
        agence.getClients().add(addedClient);
        agenceRepository.save(agence);

        //Log the username and password
        logger.info("the new client got the username: " + username + " password: " + password);

        return addedClient;
    }

    @Override
    public Client updateClient(String username,
                               String cin,
                               String nom,
                               String prenom,
                               String email,
                               String num_tele,
                               Date date_naissance,
                               boolean isActive) throws UserNotFoundException, UserExistExistException, EmailExistException {
        Client client = (Client) clientRepository.findUserByUsername(username);
        client.setCin(cin);
        client.setNom(nom);
        client.setPrenom(prenom);
        client.setEmail(email);
        client.setNum_tele(num_tele);
        client.setDate_naissance(date_naissance);
        client.setActive(isActive);

        return this.userRepository.save(client);
    }

    @Override
    public Client terminateClient(Long client_id) {
        Client client = clientRepository.findById(client_id).get();
        client.setActive(false);
        return this.clientRepository.save(client);
    }

    private String generateUsername() {
        return RandomStringUtils.randomNumeric(8);
    }

    private String generatePassword() {
        return RandomStringUtils.randomNumeric(6);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }


    private User validateNewUsernameAndEmail(String currentUsername, String username, String  email) throws UserNotFoundException, UserExistExistException, EmailExistException {

        User userByUsername =  clientRepository.findUserByUsername(username);
        User userByEmail = clientRepository.findUserByEmail(email);

        if(StringUtils.isNotBlank(currentUsername)){
            User currentUser = clientRepository.findUserByUsername(currentUsername);
            if(currentUser == null){
                throw new UserNotFoundException(NO_USER_FOUND_BY_USERNAME +  currentUsername);
            }
            if(userByUsername != null && !(currentUser.getId() + "").equals(userByUsername.getId() + "")){
                throw new UserExistExistException(USERNAME_IS_ALREADY_EXIST);
            }
            if(userByEmail != null && !(currentUser.getId() + "").equals(userByEmail.getId() + "")){
                throw new EmailExistException(EMAIL_IS_ALREADY_EXIST);
            }
            return currentUser;
        }else {
            if(userByUsername != null ){
                throw new UserExistExistException(USERNAME_IS_ALREADY_EXIST);
            }
            if(userByEmail != null){
                throw new EmailExistException(EMAIL_IS_ALREADY_EXIST);
            }

            return null;
        }
    }

    @Override
    public Client getClientByID(Long id) {
        return this.clientRepository.findById(id).get();
    }

    @Override
    public Client findClientByUsername(String username) {
        return (Client) clientRepository.findUserByUsername(username);
    }
}
