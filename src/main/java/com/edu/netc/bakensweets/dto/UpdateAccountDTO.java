package com.edu.netc.bakensweets.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAccountDTO {
    private String firstName;
    private String lastName;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthDate;
    private String gender;
    private String imgUrl;
}
