package com.ensas.ebanking.services.imlementations;

import com.ensas.ebanking.domains.User;
import com.ensas.ebanking.entities.Client;
import com.ensas.ebanking.exceptions.domain.EmailExistException;
import com.ensas.ebanking.exceptions.domain.UserExistExistException;
import com.ensas.ebanking.exceptions.domain.UserNotFoundException;
import com.ensas.ebanking.repositories.ClientRepository;
import com.ensas.ebanking.services.ClientService;
import com.ensas.ebanking.services.EmailService;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    private EmailService emailService;
    private Logger logger = LoggerFactory.getLogger(getClass());

    public ClientServiceImpl(ClientRepository clientRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    public List<Client> getClients() {
        return (List<Client>) clientRepository.findAll();
    }

    @Override
    public Client addClient(String cin,
                            String nom,
                            String prenom,
                            String email,
                            String id_agence) throws UserNotFoundException, UserExistExistException, EmailExistException, MessagingException {

        String username = generateUsername();
        String password = generatePassword();
        logger.info("the new client got the username: " + username + " password: " + password);

        //get the current agency


        // create new client
        Client client = new Client();
        client.setNom(nom);
        client.setPrenom(prenom);
        client.setCin(cin);
        client.setEmail(email);
        client.setUsername(username);
        client.setJoinDate(new Date());
        client.setType_client("individuel");
        client.setActive(true);
        client.setNotLocked(true);
        client.setPassword(encodePassword(password));
        client.setRoles(ROLE_CLIENT.name());
        client.setAuthorities(ROLE_CLIENT.getAuthorities());

        //validate the new client before  saving to database
        validateNewUsernameAndEmail(StringUtils.EMPTY, username, client.getEmail());

        //send user and password in the email to the client
        //emailService.sendNewPasswordEmail(nom + " " + prenom, username, password, email);

        //save client to database
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

    private String generateUsername() {
        return RandomStringUtils.randomAlphabetic(8);
    }

    private String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(8);
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
            if(userByUsername != null && !(currentUser.getId() + "").equals(userByUsername.getId())){
                throw new UserExistExistException(USERNAME_IS_ALREADY_EXIST);
            }
            if(userByEmail != null && !(currentUser.getId() + "").equals(userByEmail.getId())){
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
    public Client getClientByID(int id) {
        return this.clientRepository.findById(id).get();
    }
}
