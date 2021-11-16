package com.edu.netc.bakensweets.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class AccountDTO {
    @Email(message = "Email must be a valid email address")
    private String email;
    @NotBlank(message = "You must specify a password")
    private String password;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String gender;
}
