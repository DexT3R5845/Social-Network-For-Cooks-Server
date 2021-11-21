package com.edu.netc.bakensweets.controller;

import com.edu.netc.bakensweets.dto.AccountDTO;
import com.edu.netc.bakensweets.exception.CustomException;
import com.edu.netc.bakensweets.model.payload.AuthRequestResetUpdatePassword;
import com.edu.netc.bakensweets.model.payload.AuthResponse;
import com.edu.netc.bakensweets.model.payload.ValidateResetLink;
import com.edu.netc.bakensweets.service.interfaces.AccountService;
import com.edu.netc.bakensweets.service.interfaces.ModerCreationService;
import com.edu.netc.bakensweets.service.interfaces.PasswordResetTokenService;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

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

    public AuthResponse signIn(@ApiParam("Email") @RequestParam String email,
                               @ApiParam("Password") @RequestParam String password, HttpServletResponse httpServletResponse) {

        return new AuthResponse(accountService.signIn(email, password));
    }

    @PostMapping(value = "/signup")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied")})
    public ResponseEntity<String> signUp(@RequestBody AccountDTO accountDTO) {
            return ResponseEntity.ok(accountService.signUp(accountDTO));
    }

    @PostMapping(value = "/password/resetlink")
    public void resetLink(@RequestParam String email) {
        passResetTokenService.createToken(email);
    }

    @GetMapping(value = "/password/reset")
    public ValidateResetLink reset(@RequestParam String token) {
        return passResetTokenService.validateResetToken(token);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Passwords do not match")
    })
    @PutMapping("/password/reset")
    public ResponseEntity<String> resetPasswordUpdate(@RequestBody AuthRequestResetUpdatePassword modelResetUpdatePassword) {
        if (!modelResetUpdatePassword.getPassword().equals(modelResetUpdatePassword.getConfirm_password()))
            throw new CustomException(HttpStatus.BAD_REQUEST, "Passwords do not match");
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
