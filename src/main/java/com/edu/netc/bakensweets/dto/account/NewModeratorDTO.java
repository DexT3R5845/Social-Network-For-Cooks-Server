package com.edu.netc.bakensweets.dto.account;

import com.edu.netc.bakensweets.model.Gender;
import lombok.Data;

import java.util.Date;

@Data
public class NewModeratorDTO {
    private String email;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private Gender gender;
}
