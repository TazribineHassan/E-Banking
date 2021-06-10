package com.ensas.ebanking.enumeration;

import static com.ensas.ebanking.constant.Authorities.*;

public enum Role {

    ROLE_CLIENT(CLIENT_AUTHORITIES),
    ROLE_AGENT(AGENT_AUTHORITIES),
    ROLE_ADMIN(ADMIN_AUTHORITIES);

    private String[] authorities;

    Role(String... authorities) {
        this.authorities = authorities;
    }

    public String[] getAuthorities(){
        return authorities;
    }
}
