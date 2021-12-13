package com.edu.netc.bakensweets.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class Account {
    private long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Gender gender;
    private String imgUrl;
    private AccountRole accountRole;
    private boolean status;
}
