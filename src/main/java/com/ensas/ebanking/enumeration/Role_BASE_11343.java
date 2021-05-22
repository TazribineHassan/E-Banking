package com.ensas.ebanking.enumeration;

import static com.ensas.ebanking.constant.Authorities.*;

public enum Role {
    ROLE_USER(USER_AUTHORITIES),
    ROLE_HR(HR_AUTHORITIES),
    ROLE_MANAGER(MANAGER_AUTHORITIES),
    ROLE_ADMIN(MANAGER_AUTHORITIES),
    ROLE_SUPPER_ADMIN(SUPPER_ADMIN_AUTHORITIES);

    private String[] authorities;

    Role(String... authorities) {
        this.authorities = authorities;
    }

    public String[] getAuthorities(){
        return authorities;
    }
}
