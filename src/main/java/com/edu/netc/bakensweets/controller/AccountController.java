package com.edu.netc.bakensweets.controller;

import com.edu.netc.bakensweets.dto.AccountDTO;
import com.edu.netc.bakensweets.service.AccountService;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RequestMapping("/api")
@RestController
public class AccountController {
    private final AccountService accountService;

    public AccountController (AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/signin")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"), //
            @ApiResponse(code = 422, message = "Invalid username/password supplied")})
    public String signIn(@ApiParam("Email") @RequestParam String email,
                         @ApiParam("Password") @RequestParam String password, HttpServletResponse httpServletResponse) {

       return accountService.signIn(email, password);
    }

    @PostMapping(value = "/signup")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied")})
    public ResponseEntity<String> signUp(@ApiParam("Signup Account")@RequestBody AccountDTO accountDTO) {

        return ResponseEntity.ok(accountService.signUp(accountDTO));
    }

    @GetMapping("/test")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied")})
    public String test(Principal principal){
        return principal.getName();
    }
}
