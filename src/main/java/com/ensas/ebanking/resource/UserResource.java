package com.ensas.ebanking.resource;


import com.ensas.ebanking.domains.User;
import com.ensas.ebanking.domains.UserPrincipal;
import com.ensas.ebanking.entities.Admin;
import com.ensas.ebanking.exceptions.domain.EmailExistException;
import com.ensas.ebanking.exceptions.domain.ExceptionHandling;
import com.ensas.ebanking.exceptions.domain.UserExistExistException;
import com.ensas.ebanking.exceptions.domain.UserNotFoundException;
import com.ensas.ebanking.services.UserService;
import com.ensas.ebanking.utilities.JWTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

import static com.ensas.ebanking.constant.SecurityConstant.JWT_TOKEN_HEADER;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path= {"/","/user"})
public class UserResource extends ExceptionHandling {

    private UserService userService;
    private AuthenticationManager authenticationManager;
    private JWTokenProvider jwTokenProvider;

    @Autowired
    public UserResource(UserService userService, AuthenticationManager authenticationManager, JWTokenProvider jwTokenProvider) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwTokenProvider = jwTokenProvider;
    }

    @PostMapping("/admin/register")
    public ResponseEntity<Admin> register(@RequestBody Admin admin) throws UserNotFoundException, UserExistExistException, EmailExistException, MessagingException {
        Admin newAdmin =  userService.register(admin.getCin(), admin.getNom(), admin.getPrenom(), admin.getUsername(), admin.getEmail());
        return new ResponseEntity<>(newAdmin, OK);
    }

    @PostMapping("/login")
    public ResponseEntity<User>  login(@RequestBody Admin admin)  {
        //System.out.println("Befor authentication");
        authentication(admin.getUsername(), admin.getPassword());
        //System.out.println("After authentication");
        User loginUser = userService.findUserByUsername(admin.getUsername());
        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
        return new ResponseEntity<>(loginUser, jwtHeader, OK);
    }

    private void authentication(String username, String password) {
        System.out.println("inside authentication TOP");
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        System.out.println("inside authentication BOTTOM");
    }

    private HttpHeaders getJwtHeader(UserPrincipal userPrincipal) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwTokenProvider.generateJwtToken(userPrincipal));
        return headers;
    }

}
