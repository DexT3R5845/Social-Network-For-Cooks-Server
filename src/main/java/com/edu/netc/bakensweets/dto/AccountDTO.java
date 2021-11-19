package com.edu.netc.bakensweets.dto;

import com.edu.netc.bakensweets.annotation.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    @NotBlank(message = "Email is mandatory")
    @Email
    private String email;
    @ValidPassword
    @NotBlank(message = "Password is mandatory")
    private String password;
    @Pattern(regexp = "[^\\s]+", message = "Firstname must not contain space")
    @Pattern(regexp = "[^0-9]+", message = "Firstname must not contain numbers")
    @NotBlank(message = "Firstname is mandatory")
    private String firstName;
    @Pattern(regexp = "[^\\s]+", message = "Lastname must not contain space")
    @Pattern(regexp = "[^0-9]+", message = "Lastname must not contain numbers")
    @NotBlank(message = "Lastname is mandatory")
    private String lastName;
    @NotNull(message = "Birthdate is mandatory")
    private Date birthDate;
    @Pattern(regexp = "^[F|M]$", message = "Gender must be contain F or M")
    @NotBlank(message = "Gender is mandatory")
    private String gender;
}
