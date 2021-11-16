package com.edu.netc.bakensweets.controller;

import com.edu.netc.bakensweets.dto.AccountDTO;
import com.edu.netc.bakensweets.model.Account;
import com.edu.netc.bakensweets.service.AccountService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AccountController {
    private AccountService accountService;

    public AccountController (AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/signin")
    public String signIn(@RequestParam String email,
                         @RequestParam String password) {
       return accountService.signIn(email,password);
    }

    @PostMapping(value = "/signup")
    public String signUp(@RequestBody AccountDTO accountDTO) {

        return accountService.createAccount(accountDTO);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ADMIN')")
    @GetMapping("/test")
    public String test(){
        return "hello";
    }
}
