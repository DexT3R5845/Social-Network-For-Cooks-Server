package com.edu.netc.bakensweets.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@RequiredArgsConstructor
public class Account {
    @Id
    private long id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private Gender gender;
    private String imgUrl;
    private AccountRole accountRole;
}
