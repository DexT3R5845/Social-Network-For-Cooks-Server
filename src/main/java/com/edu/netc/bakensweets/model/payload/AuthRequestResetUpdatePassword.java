package com.edu.netc.bakensweets.model.payload;

import com.edu.netc.bakensweets.annotation.PasswordValueMatch;
import com.edu.netc.bakensweets.annotation.ValidPassword;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@PasswordValueMatch.List({
        @PasswordValueMatch(
                field = "password",
                fieldMatch = "confirmPassword",
                message = "Passwords do not match!"
        )
})
@Data
public class AuthRequestResetUpdatePassword {
    @NotNull(message = "Token is mandatory")
    @NotBlank(message = "Token is mandatory")
    private String token;
    @NotNull(message = "Password is mandatory")
    @ValidPassword
    @NotBlank(message = "Password is mandatory")
    private String password;
    @NotNull(message = "Confirm password is mandatory")
    @ValidPassword
    @NotBlank(message = "Confirm password is mandatory")
    private String confirmPassword;
}
