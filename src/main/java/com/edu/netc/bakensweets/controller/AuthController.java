package com.edu.netc.bakensweets.controller;

import com.edu.netc.bakensweets.dto.AccountDTO;
import com.edu.netc.bakensweets.model.payload.AuthRequestResetUpdatePassword;
import com.edu.netc.bakensweets.model.payload.AuthResponse;
import com.edu.netc.bakensweets.model.payload.ValidateResetLink;
import com.edu.netc.bakensweets.service.AccountService;
import com.edu.netc.bakensweets.service.PasswordResetTokenService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Validated
@RequestMapping("/api/auth")
@RestController
public class AuthController {
    private final AccountService accountService;
    private final PasswordResetTokenService passResetTokenService;

    public AuthController(AccountService accountService, PasswordResetTokenService passResetTokenService) {
        this.accountService = accountService;
        this.passResetTokenService = passResetTokenService;
    }

    @PostMapping("/signin")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 401, message = "Invalid username/password supplied"),
            @ApiResponse(code = 422, message = "Need enter captcha")})
    public AuthResponse signIn(@Email(message = "Email format is invalid") @NotNull(message = "Email is mandatory") @RequestParam String email,
                               @NotNull(message = "Password is mandatory") @NotBlank(message = "Password is mandatory") @RequestParam String password,
                               @RequestParam("g-recaptcha-response") String captcha, HttpServletRequest httpServletRequest) {
        return new AuthResponse(accountService.signIn(email, password, captcha, httpServletRequest.getRemoteAddr()));
    }

    @PostMapping(value = "/signup")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied")})
    public ResponseEntity<String> signUp(@Valid @RequestBody AccountDTO accountDTO) {
        return ResponseEntity.ok(accountService.signUp(accountDTO));
    }

    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong")})
    @PostMapping(value = "/password/resetlink")
    public void resetLink(@RequestParam @Email(message = "Email format is invalid") @NotNull(message = "Email is mandatory") @NotBlank(message = "Email is mandatory") String email) {
        passResetTokenService.createToken(email);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 410, message = "The link was used"),
            @ApiResponse(code = 404, message = "Token link not found")})
    @GetMapping(value = "/password/reset")
    public ValidateResetLink reset(@RequestParam @NotNull(message = "Token link is mandatory") @NotBlank(message = "Token link is mandatory") String token) {
        return passResetTokenService.validateResetToken(token);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong")
    })
    @PutMapping("/password/reset")
    public ResponseEntity<String> resetPasswordUpdate(@Valid @RequestBody AuthRequestResetUpdatePassword modelResetUpdatePassword) {
        passResetTokenService.changePassword(modelResetUpdatePassword);
        return ResponseEntity.ok("Password successful update");
    }
}
