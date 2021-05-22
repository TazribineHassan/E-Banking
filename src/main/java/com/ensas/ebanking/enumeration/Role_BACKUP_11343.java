package com.ensas.ebanking.enumeration;

import static com.ensas.ebanking.constant.Authorities.*;

public enum Role {
<<<<<<< HEAD
    ROLE_CLIENT(CLIENT_AUTHORITIES),
    ROLE_AGENT(AGENT_AUTHORITIES),
    ROLE_ADMIN(ADMIN_AUTHORITIES);
=======
    ROLE_USER(USER_AUTHORITIES);
>>>>>>> 4b8a37f2c3cc2736b533fc616f46bf3b7a1c6b87

    private String[] authorities;

    Role(String... authorities) {
        this.authorities = authorities;
    }

    public String[] getAuthorities(){
        return authorities;
    }
}
