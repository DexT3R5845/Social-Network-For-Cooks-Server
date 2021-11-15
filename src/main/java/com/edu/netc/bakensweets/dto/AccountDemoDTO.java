package com.edu.netc.bakensweets.dto;

import com.edu.netc.bakensweets.model.Account;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class AccountDemoDTO {
    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String gender;
    private String imgUrl;

    public static AccountDemoDTO accountToDTO(Account account) {
        return AccountDemoDTO
                .builder()
                .id(account.getId())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .birthDate(account.getBirthDate())
                .gender(account.getGender().name())
                .imgUrl(account.getImgUrl())
                .build();
    }
}
