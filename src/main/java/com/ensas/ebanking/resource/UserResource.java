package com.ensas.ebanking.resource;


import com.ensas.ebanking.domains.User;
import com.ensas.ebanking.domains.UserPrincipal;
import com.ensas.ebanking.entities.Admin;
import com.ensas.ebanking.entities.Client;
import com.ensas.ebanking.entities.LoginUser;
import com.ensas.ebanking.exceptions.domain.*;
import com.ensas.ebanking.services.UserService;
import com.ensas.ebanking.utilities.JWTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import static com.ensas.ebanking.constant.SecurityConstant.JWT_TOKEN_HEADER;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path= {"/","/user"})
public class UserResource extends ExceptionHandling {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTokenProvider jwTokenProvider;

    @Autowired
    public UserResource(UserService userService, AuthenticationManager authenticationManager, JWTokenProvider jwTokenProvider) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwTokenProvider = jwTokenProvider;
    }

    @GetMapping("/current/dtails")
    public ResponseEntity<User> getCurrentUser(Principal principal){
        String username = principal.getName();
        User currentUser = userService.findUserByUsername(username);
        return new ResponseEntity<>(currentUser, OK);
    }

    @PostMapping("/admin/register")
    public ResponseEntity<Admin> register(@RequestBody Admin admin) throws UserNotFoundException, UserExistExistException, EmailExistException, MessagingException {
        Admin newAdmin =  userService.register(admin.getCin(), admin.getNom(), admin.getPrenom(), admin.getUsername(), admin.getEmail(), new Date());
        return new ResponseEntity<>(newAdmin, OK);
    }

    @PostMapping("/login")
    public ResponseEntity<User>  login(@RequestBody LoginUser user)  {
        authentication(user.getUsername(), user.getPassword());
        User loginUser = userService.findUserByUsername(user.getUsername());
        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
        return new ResponseEntity<>(loginUser, jwtHeader, OK);
    }

    @PutMapping("/resetpassword")
    public ResponseEntity<Boolean> resetPassword(@RequestParam(name = "old_password") String old_password,
                                                 @RequestParam(name = "new_password")String new_password,
                                                 Principal principal) throws Exception {
        // get the current client
        String username = principal.getName();
        if (username == null)
            throw new Exception("Erreur!");

        // try to authenticate with the real user name and the old password provided
        try {
            authentication(username, old_password);
        }catch (Exception e){
            throw new PasswordInvalideException();
        }

        // update the password
        User user = userService.updatePassword(username, new_password);
        if (user == null)
            throw new Exception("Erreur!");;

        return new ResponseEntity<>(true, OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<User>>  listUsers(){

        List<User> list = userService.getUsers();
        return new ResponseEntity<>(list, OK);
    }

    private void authentication(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    private HttpHeaders getJwtHeader(UserPrincipal userPrincipal) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwTokenProvider.generateJwtToken(userPrincipal));
        return headers;
    }

}
