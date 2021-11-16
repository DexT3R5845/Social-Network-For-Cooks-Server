package com.edu.netc.bakensweets.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class Credentials {
    private long id;
    private String email;
    private String password;
    private String access_token;
    }
