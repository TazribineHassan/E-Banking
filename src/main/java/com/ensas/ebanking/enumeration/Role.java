package com.ensas.ebanking.enumeration;

import static com.ensas.ebanking.constant.Authorities.*;

public enum Role {
    ROLE_USER(USER_AUTHORITIES);

    private String[] authorities;

    Role(String... authorities) {
        this.authorities = authorities;
    }

    public String[] getAuthorities(){
        return authorities;
    }
}
