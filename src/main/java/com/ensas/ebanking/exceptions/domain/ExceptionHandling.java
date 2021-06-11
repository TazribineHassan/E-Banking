package com.ensas.ebanking.exceptions.domain;

import com.auth0.jwt.exceptions.TokenExpiredException;

import com.ensas.ebanking.domains.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.NoResultException;
import java.io.IOException;
import java.util.Objects;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ExceptionHandling implements ErrorController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    public static final String ACCOUNT_LOCKED = "Your account has been locked. Please contact administration";
    public static final String METHOD_IS_NOT_ALLOWED = "This request is not allowed on this endpoint. Please send a '%s' request";
    public static final String INTERNAL_SERVER_ERROR_MSG = "An error occurred while processing the request";
    public static final String INCORRECT_CREDENTIALS = "Username / password incorrect. Please try again";
    public static final String ACCOUNT_DISABLED = "Your account has been disabled. If this is an error, please contact administration";
    public static final String ERROR_PROCESSING_FILE = "Error occurred while processing file";
    public static final String NOT_ENOUGH_PERMISSION = "You don't have the permission";
    public static final String ERROR_PATH = "/error";

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<HttpResponse> accountDisabledException(){

        return createHttpResponse(BAD_REQUEST, ACCOUNT_DISABLED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<HttpResponse> badCredentialsException(){

        return createHttpResponse(HttpStatus.BAD_REQUEST, INCORRECT_CREDENTIALS);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<HttpResponse> accessDeniedException(){

        return createHttpResponse(FORBIDDEN, NOT_ENOUGH_PERMISSION);
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<HttpResponse> lockedException(){
        return createHttpResponse(UNAUTHORIZED, ACCOUNT_LOCKED);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<HttpResponse> tokenExpiredException(TokenExpiredException e){
        return createHttpResponse(UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler(EmailExistException.class)
    public ResponseEntity<HttpResponse> emailExistException(EmailExistException e){
        return createHttpResponse(BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(UserExistExistException.class)
    public ResponseEntity<HttpResponse> userExistExistException(UserExistExistException e){
        return createHttpResponse(BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<HttpResponse> emailNotFoundException(EmailNotFoundException e){
        return createHttpResponse(BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<HttpResponse> userNotFoundException(UserNotFoundException e){
        return createHttpResponse(BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<HttpResponse> methodNotSupportedException(HttpRequestMethodNotSupportedException e){
        HttpMethod method = Objects.requireNonNull(e.getSupportedHttpMethods()).iterator().next();
        return createHttpResponse(METHOD_NOT_ALLOWED, String.format(METHOD_IS_NOT_ALLOWED, method));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponse>  internalServerErrorException(Exception e){
        logger.error(e.getMessage());
        return createHttpResponse(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MSG);
    }

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<HttpResponse>  noResultException(NoResultException e){
        logger.error(e.getMessage());
        return createHttpResponse(NOT_FOUND, e.getMessage() );
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<HttpResponse>  iOException(IOException e){
        logger.error(e.getMessage());
        return createHttpResponse(INTERNAL_SERVER_ERROR, ERROR_PROCESSING_FILE );
    }

    @ExceptionHandler(value = BalanceNotEnoughException.class)
    public ResponseEntity<HttpResponse>  balanceNotEnoughException(BalanceNotEnoughException e){
        logger.error(e.getMessage());
        return createHttpResponse(BAD_REQUEST, e.getMessage() );
    }

    @ExceptionHandler(value = AccountNotFoundException.class)
    public ResponseEntity<HttpResponse>  accountNotFoundException(AccountNotFoundException e){
        logger.error(e.getMessage());
        return createHttpResponse(BAD_REQUEST, e.getMessage() );
    }

    @ExceptionHandler(value = CodeNotValideException.class)
    public ResponseEntity<HttpResponse>  codeNotValideException(CodeNotValideException e){
        logger.error(e.getMessage());
        return createHttpResponse(BAD_REQUEST, e.getMessage() );
    }

    @ExceptionHandler(value = SessionExpiredException.class)
    public ResponseEntity<HttpResponse>  sessionExpiredException(SessionExpiredException e){
        logger.error(e.getMessage());
        return createHttpResponse(BAD_REQUEST, e.getMessage() );
    }

    @ExceptionHandler(value = FactureNotFoundException.class)
    public ResponseEntity<HttpResponse>  factureNotFoundException(FactureNotFoundException e){
        logger.error(e.getMessage());
        return createHttpResponse(BAD_REQUEST, e.getMessage() );
    }

    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message){
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(), message), httpStatus) ;
    }

    @RequestMapping(ERROR_PATH)
    public ResponseEntity<HttpResponse>  notFound(){

        return createHttpResponse(NOT_FOUND, "The page was not found" );
    }



    @Override
    public String getErrorPath() {
        return null;
    }
}
