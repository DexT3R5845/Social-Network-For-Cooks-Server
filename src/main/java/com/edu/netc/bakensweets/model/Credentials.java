package com.edu.netc.bakensweets.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class Credentials {
    private long id;
    private String email;
    private String password;
    }
