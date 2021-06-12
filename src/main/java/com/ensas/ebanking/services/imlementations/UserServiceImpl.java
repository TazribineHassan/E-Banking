package com.ensas.ebanking.services.imlementations;

import com.ensas.ebanking.domains.User;
import com.ensas.ebanking.domains.UserPrincipal;
import com.ensas.ebanking.entities.Admin;
import com.ensas.ebanking.entities.Agence;
import com.ensas.ebanking.exceptions.domain.EmailExistException;
import com.ensas.ebanking.exceptions.domain.EmailNotFoundException;
import com.ensas.ebanking.exceptions.domain.UserExistExistException;
import com.ensas.ebanking.exceptions.domain.UserNotFoundException;
import com.ensas.ebanking.repositories.UserRepository;
import com.ensas.ebanking.services.EmailService;
import com.ensas.ebanking.services.LoginAttemptService;
import com.ensas.ebanking.services.UserService;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static com.ensas.ebanking.constant.FileConstant.*;
import static com.ensas.ebanking.constant.UserImplementationConstant.*;
import static com.ensas.ebanking.enumeration.Role.ROLE_ADMIN;


@Service
@Transactional
@Qualifier("UserDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private LoginAttemptService loginAttemptService;
    private EmailService emailService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, LoginAttemptService loginAttemptService, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.loginAttemptService = loginAttemptService;
        this.emailService = emailService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if(user == null){
            logger.error(NO_USER_FOUND_BY_USERNAME + username);
            throw new UsernameNotFoundException(NO_USER_FOUND_BY_USERNAME + username);
        }else {
            validateLoginAttempt(user);
            user.setLastLoginDateDisplay(user.getLastLoginDate());
            user.setLastLoginDate(new Date());
            userRepository.save(user);
            UserPrincipal userPrincipal = new UserPrincipal(user);
            logger.info("Returning found by username :" + username);
            return  userPrincipal;
        }
    }

    private void validateLoginAttempt(User user) {
        if(user.isNotLocked()){
            if(loginAttemptService.hasExceededMaxAttempts(user.getUsername())){
                user.setNotLocked(false);
            }else {
                user.setNotLocked(true);
            }
        }else {
            loginAttemptService.evictUserFromLoginAttemptCache(user.getUsername());
        }
    }

    @Override
    public Admin register(String cin, String nom, String prenom, String username, String email, Date dob) throws UserNotFoundException, UserExistExistException, EmailExistException, MessagingException {
        validateNewUsernameAndEmail(StringUtils.EMPTY, username, email);
        Agence agence = new Agence();
        agence.setNom("SAFI 2, Agence Zaouya");
        Admin agent = new Admin();

        String password = generatePassword();
        agent.setCin(cin);
        agent.setNom(nom);
        agent.setPrenom(prenom);
        agent.setUsername(username);
        agent.setEmail(email);
        agent.setJoinDate(new Date());
        agent.setPassword(encodePassword(password));
        agent.setDate_naissance(dob);
        agent.setActive(true);
        agent.setNotLocked(true);
        agent.setRoles(ROLE_ADMIN.name());
        agent.setAuthorities(ROLE_ADMIN.getAuthorities());
        //emailService.sendNewPasswordEmail(nom, password, email);
        logger.info("New user password: " + password);
        this.userRepository.save(agent);
        return agent;
    }

    @Override
    public List<User> getUsers() {
        return (List<User>) this.userRepository.findAll();
    }

    @Override
    public User findUserByUsername(String username) {
        return this.userRepository.findUserByUsername(username);
    }

    @Override
    public User findUserByEmail(String email) {
        return this.userRepository.findUserByEmail(email);
    }

    @Override
    public User addNewUser(String firstName, String lastName, String username, String email, String role, boolean isNonLocked, boolean isActive, MultipartFile file) throws UserNotFoundException, UserExistExistException, EmailExistException, IOException {
        return null;
    }

    @Override
    public User updateUser(String currentUsername, String newFirstName, String newLastName, String newUsername, String newEmail, String role, boolean isNonLocked, boolean isActive, MultipartFile file) throws UserNotFoundException, UserExistExistException, EmailExistException, IOException {
        return null;
    }

    @Override
    public void deleteUser(int id) {

    }

    @Override
    public void resetPassword(String email) throws EmailNotFoundException, MessagingException {

    }

    @Override
    public User updateProfileImage(String username, MultipartFile file) throws UserNotFoundException, UserExistExistException, EmailExistException, IOException {
        return null;
    }

    @Override
    public User updatePassword(String username, String new_password) {
        User user = userRepository.findUserByUsername(username);
        user.setPassword(encodePassword(new_password));
        return userRepository.save(user);
    }

    private User validateNewUsernameAndEmail(String currentUsername, String username, String  email) throws UserNotFoundException, UserExistExistException, EmailExistException {

        User userByUsername = findUserByUsername(username);
        User userByEmail = findUserByEmail(email);

        if(StringUtils.isNotBlank(currentUsername)){
            User currentUser = findUserByUsername(currentUsername);
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

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(8);
    }

    private String generateCodeEmployee() {
        return RandomStringUtils.randomNumeric(10);
    }

    private String setProfileImageUrl(String username) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(USER_IMAGE_PATH + username + FORWARD_SLASH + username + DOT + JPG_EXTENSION ).toUriString();
    }

    private String getTemporaryProfileImageUrl(String username) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_USER_IMAGE_PATH + username).toUriString();
    }
}
