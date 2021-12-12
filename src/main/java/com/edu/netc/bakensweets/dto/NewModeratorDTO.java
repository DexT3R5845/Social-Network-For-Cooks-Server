package com.edu.netc.bakensweets.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class NewModeratorDTO {
    @NotNull(message = "Email is mandatory")
    @NotBlank(message = "Email is mandatory")
    @Email(message = "invalid email")
    private String email;
    @NotNull(message = "Firstname is mandatory")
    @NotBlank(message = "Firstname is mandatory")
    @Pattern(regexp = "[^\\s]+", message = "Firstname must not contain space")
    @Pattern(regexp = "[^0-9]+", message = "Firstname must not contain numbers")
    private String firstName;
    @NotNull(message = "Lastname is mandatory")
    @NotBlank(message = "Lastname is mandatory")
    @Pattern(regexp = "[^\\s]+", message = "Lastname must not contain space")
    @Pattern(regexp = "[^0-9]+", message = "Lastname must not contain numbers")
    private String lastName;
    @NotNull(message = "Birthdate is mandatory")
    private Date birthDate;
    @NotNull(message = "Gender is mandatory")
    @NotBlank(message = "Gender is mandatory")
    @Pattern(regexp = "^[F|M]$", message = "Gender must be contain F or M")
    private String gender;
}
