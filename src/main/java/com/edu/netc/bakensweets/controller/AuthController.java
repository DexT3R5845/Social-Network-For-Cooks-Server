package com.edu.netc.bakensweets.controller;

import com.edu.netc.bakensweets.dto.AccountDTO;
import com.edu.netc.bakensweets.model.payload.AuthRequestResetUpdatePassword;
import com.edu.netc.bakensweets.model.payload.AuthResponse;
import com.edu.netc.bakensweets.model.payload.ValidateResetLink;
import com.edu.netc.bakensweets.service.interfaces.AccountService;
import com.edu.netc.bakensweets.service.interfaces.ModerCreationService;
import com.edu.netc.bakensweets.service.interfaces.PasswordResetTokenService;
import io.swagger.annotations.ApiParam;
import com.edu.netc.bakensweets.service.AccountService;
import com.edu.netc.bakensweets.service.PasswordResetTokenService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final ModerCreationService moderCreationService;

    public AuthController (AccountService accountService, PasswordResetTokenService passResetTokenService, ModerCreationService moderCreationService) {
        this.accountService = accountService;
        this.passResetTokenService = passResetTokenService;
        this.moderCreationService = moderCreationService;
    }

    @PostMapping("/signin")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 401, message = "Invalid username/password supplied"),
            @ApiResponse(code = 422, message = "Need enter captcha")})
    public AuthResponse signIn(@Email(message = "Email format is invalid") @NotNull(message = "Email is mandatory") @RequestParam String email,
                               @NotNull(message = "Password is mandatory") @NotBlank(message = "Password is mandatory") @RequestParam String password,
                               @RequestParam(value = "g-recaptcha-response", required = false) String captcha, HttpServletRequest httpServletRequest) {
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
    public boolean reset(@RequestParam @NotNull(message = "Token link is mandatory") @NotBlank(message = "Token link is mandatory") String token) {
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



    @GetMapping(value = "/password/creation")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "passwords do not match"),
            @ApiResponse(code = 410, message = "invalid token"),
            @ApiResponse(code = 404, message = "row is not found in db")})
    public HttpStatus passwordCreation(@RequestParam String token) {
        return moderCreationService.validateModerToken(token);
    }


    @PutMapping("/password/creation")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "passwords do not match"),
            @ApiResponse(code = 410, message = "invalid token")})
    public ResponseEntity<String> passwordCreationUpdate(@RequestBody AuthRequestResetUpdatePassword modelResetUpdatePassword) {
        if (!modelResetUpdatePassword.getPassword().equals(modelResetUpdatePassword.getConfirm_password()))
            throw new CustomException(HttpStatus.BAD_REQUEST, "Passwords do not match");
        moderCreationService.createAccount(modelResetUpdatePassword);
        return ResponseEntity.ok("Moder account confirmed and created");
    }


    @PreAuthorize("ROLE_USER")
    @GetMapping("/test")
    public String test() {
        return null;
    }

}
