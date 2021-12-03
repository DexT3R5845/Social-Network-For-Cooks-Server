package com.edu.netc.bakensweets.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AccountPersonalInfoDTO {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String gender;
    private String imgUrl;
    private boolean status;
}