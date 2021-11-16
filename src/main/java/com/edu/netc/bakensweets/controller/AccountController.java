package com.edu.netc.bakensweets.controller;

import com.edu.netc.bakensweets.dto.AccountDTO;
import com.edu.netc.bakensweets.model.Account;
import com.edu.netc.bakensweets.security.JwtTokenProvider;
import com.edu.netc.bakensweets.service.AccountService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@RestController
@RequestMapping("/api")
public class AccountController {
    private AccountService accountService;
    private JwtTokenProvider tokenProvider;

    public AccountController(AccountService accountService, JwtTokenProvider tokenProvider) {
        this.accountService = accountService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/signin")
    public String signIn(@RequestParam String email,
                         @RequestParam String password) {
        return accountService.signIn(email, password);
    }

    @PostMapping(value = "/signup")
    public String signUp(@RequestBody AccountDTO accountDTO) {
        return accountService.createAccount(accountDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR')")
    @PutMapping(value = "/user")
    public String updateProfile(@RequestBody AccountDTO accountDTO, HttpServletRequest request) {
        String token = tokenProvider.resolveToken(request);
        return accountService.updateProfile(accountDTO, token);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR')")
    @PutMapping(value = "/user/changePassword")
    public String changePassword(@RequestParam String oldPassword,
                                 @RequestParam String newPassword,
                                 HttpServletRequest request) {
        String token = tokenProvider.resolveToken(request);
        return accountService.changePassword(oldPassword, newPassword, token);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ADMIN')")
    @GetMapping("/test")
    public String test() {
        return "hello";
    }
}
