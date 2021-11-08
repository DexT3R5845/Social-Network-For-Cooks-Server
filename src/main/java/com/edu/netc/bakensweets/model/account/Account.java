package com.edu.netc.bakensweets.model.account;

import com.edu.netc.bakensweets.model.account.dto.AccountSignUpDto;
import com.edu.netc.bakensweets.model.role.Role;
import com.edu.netc.bakensweets.model.credentials.Credentials;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private int id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private Gender gender;
    private String image;
    private Role role;
    private Credentials credentials;
}

