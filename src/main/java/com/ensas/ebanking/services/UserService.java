package com.ensas.ebanking.services;

import com.ensas.ebanking.domains.User;
import com.ensas.ebanking.entities.Admin;
import com.ensas.ebanking.exceptions.domain.EmailExistException;
import com.ensas.ebanking.exceptions.domain.EmailNotFoundException;
import com.ensas.ebanking.exceptions.domain.UserExistExistException;
import com.ensas.ebanking.exceptions.domain.UserNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface UserService {
    Admin register(String cin, String nom, String prenom, String username, String email, Date dob) throws UserNotFoundException, UserExistExistException, EmailExistException, MessagingException;
    List<User> getUsers();
    User findUserByUsername(String username);
    User findUserByEmail(String email);
    User addNewUser(String firstName, String lastName, String username, String email, String role, boolean isNonLocked, boolean isActive, MultipartFile file) throws UserNotFoundException, UserExistExistException, EmailExistException, IOException;
    User updateUser(String currentUsername, String newFirstName, String newLastName, String newUsername, String newEmail, String role, boolean isNonLocked, boolean isActive, MultipartFile file) throws UserNotFoundException, UserExistExistException, EmailExistException, IOException;
    void deleteUser(int id);
    void resetPassword(String email) throws EmailNotFoundException, MessagingException;
    User updateProfileImage(String username, MultipartFile file) throws UserNotFoundException, UserExistExistException, EmailExistException, IOException;

    User updatePassword(String username, String new_password);
}
