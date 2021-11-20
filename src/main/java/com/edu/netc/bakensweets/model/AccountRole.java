package com.edu.netc.bakensweets.model;

import org.springframework.security.core.GrantedAuthority;

public enum AccountRole implements GrantedAuthority {
    ROLE_USER, ROLE_MODERATOR, ROLE_ADMIN, ROLE_BAN;
    @Override
    public String getAuthority() {
        return name();
    }
}
