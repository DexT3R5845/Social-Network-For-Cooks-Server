package com.edu.netc.bakensweets.model.account.dto;

import com.edu.netc.bakensweets.model.account.Account;
import com.edu.netc.bakensweets.model.account.Gender;
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
public class AccountSignUpDto {

    private String firstName;
    private String lastName;
    private Date birthDate;
    private Gender gender;
    private Credentials credentials;

    public static AccountSignUpDto convertToDto(Account account) {
        return AccountSignUpDto.builder()
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .birthDate(account.getBirthDate())
                .gender(account.getGender())
                .credentials(account.getCredentials())
                .build();
    }
}
