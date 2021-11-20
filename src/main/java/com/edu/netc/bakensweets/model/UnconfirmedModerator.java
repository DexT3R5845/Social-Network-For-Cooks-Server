package com.edu.netc.bakensweets.model;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class UnconfirmedModerator {
    @NonNull
    private String moderToken;
    @NonNull
    private LocalDateTime expireDate;
    private String email;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private Gender gender;
}
