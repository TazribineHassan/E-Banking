package com.ensas.ebanking.constant;

public class SecurityConstant {
    public static final long EXPIRATION_TIME =  5 * 24 * 60 * 60 * 1000;
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
    public static final String E_BANKING = "E-Banking";
    public static final String GET_ARRAYS_ADMINISTRATION = "USER Management Portal";
    public static final String AUTHORITIES = "Authorities";
    public static final String FORBIDDEN_MESSAGE = "You need to log in to access this page";
    public static final String ACCESS_DENIED_MESSAGE = "You do not have permission to access to this page";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
    public static final String[] PUBLIC_URLS = {"/user/login", "/user/admin/register","/Admin/**","/vendor/**"
            ,"/css/**", "/img/**","/js/**","/favicon.ico", "/session/**"
    };
    //public static final String[] PUBLIC_URLS = {"**"};
}
