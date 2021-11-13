package com.edu.netc.bakensweets.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Credentials {
    private long id;
    private String email;
    private String password;
}
