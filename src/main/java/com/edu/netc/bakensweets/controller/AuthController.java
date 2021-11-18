package com.edu.netc.bakensweets.controller;

import com.edu.netc.bakensweets.dto.AccountDTO;
import com.edu.netc.bakensweets.exception.DataExpiredException;
import com.edu.netc.bakensweets.model.payload.AuthRequestResetUpdatePassword;
import com.edu.netc.bakensweets.model.payload.AuthResponse;
import com.edu.netc.bakensweets.model.payload.ValidateResetLink;
import com.edu.netc.bakensweets.service.AccountService;
import com.edu.netc.bakensweets.service.PasswordResetTokenService;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

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
            @ApiResponse(code = 400, message = "Something went wrong"), //
            @ApiResponse(code = 422, message = "Invalid username/password supplied")})
    public AuthResponse signIn(@ApiParam("Email") @RequestParam String email,
                               @ApiParam("Password") @RequestParam String password, HttpServletResponse httpServletResponse) {

       return new AuthResponse(accountService.signIn(email, password));
    }

    @PostMapping(value = "/signup")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied")})
    public ResponseEntity<String> signUp(@ApiParam("Signup Account")@RequestBody AccountDTO accountDTO) {

        return ResponseEntity.ok(accountService.signUp(accountDTO));
    }

    @PostMapping(value = "/password/resetlink")
    public void resetLink(@RequestParam String email){
        passResetTokenService.createToken(email);
    }

    @GetMapping(value = "/password/reset")
    public ValidateResetLink reset(@RequestParam String token){
        return passResetTokenService.validateResetToken(token);
    }

    @PutMapping("/password/reset")
    public ValidateResetLink resetPasswordUpdate(@RequestBody AuthRequestResetUpdatePassword modelResetUpdatePassword){
        if(modelResetUpdatePassword.getPassword() != modelResetUpdatePassword.getConfirm_password()) throw new DataExpiredException("Reset password link expired");
        return passResetTokenService.validateResetToken(modelResetUpdatePassword.getToken());
    }

    @PreAuthorize("ROLE_USER")
    @GetMapping("/test")
    public String test(){
        return null;
    }

}
