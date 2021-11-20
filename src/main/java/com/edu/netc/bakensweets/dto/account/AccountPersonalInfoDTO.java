package com.edu.netc.bakensweets.dto.account;

import lombok.Data;

import java.util.Date;

@Data
public class AccountPersonalInfoDTO {
    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String gender;
    private String imgUrl;
    private boolean status;
}