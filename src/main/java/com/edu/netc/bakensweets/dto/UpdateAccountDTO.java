package com.edu.netc.bakensweets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAccountDTO {
    private String firstName;
    private String lastName;
    private String birthDate;
    private String gender;
    private String imgUrl;
}
