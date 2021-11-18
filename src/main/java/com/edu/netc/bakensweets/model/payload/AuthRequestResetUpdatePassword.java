package com.edu.netc.bakensweets.model.payload;

import lombok.Data;

@Data
public class AuthRequestResetUpdatePassword {
    private String token;
    private String password;
    private String confirm_password;
}
