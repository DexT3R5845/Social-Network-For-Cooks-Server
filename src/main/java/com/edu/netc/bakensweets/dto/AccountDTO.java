package com.edu.netc.bakensweets.dto;

import lombok.Data;
import java.util.Date;

@Data
public class AccountDTO {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String gender;
}
