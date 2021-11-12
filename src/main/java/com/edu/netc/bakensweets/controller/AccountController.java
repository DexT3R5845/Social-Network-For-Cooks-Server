package com.edu.netc.bakensweets.controller;

import com.edu.netc.bakensweets.model.Account;
import com.edu.netc.bakensweets.service.AccountService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {
    private AccountService accountService;

    public AccountController (AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/signin")
    public String signIn(@RequestParam String email,
                                 @RequestParam String password) {

       return accountService.signin(email,password);
    }

    @GetMapping(value = "/signup")
    public Account signUp(@RequestParam String email) {

        return null;
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ADMIN')")
    @GetMapping("/test")
    public String test(){
        return "hello";
    }
}
