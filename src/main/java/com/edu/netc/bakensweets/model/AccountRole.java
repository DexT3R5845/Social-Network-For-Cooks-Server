package com.edu.netc.bakensweets.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;

public enum AccountRole implements GrantedAuthority {
    ROLE_USER,ROLE_ADMIN;
    @Override
    public String getAuthority() {
        return name();
    }
}
