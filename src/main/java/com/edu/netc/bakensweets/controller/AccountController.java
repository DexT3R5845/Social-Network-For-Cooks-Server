package com.edu.netc.bakensweets.controller;

import com.edu.netc.bakensweets.model.account.Account;
import com.edu.netc.bakensweets.model.credentials.Credentials;
import com.edu.netc.bakensweets.service.AccountService;
import com.edu.netc.bakensweets.model.account.dto.AccountSignUpDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
    private AccountService accountService;

    public AccountController (AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(value = "/sign-in")
    public ResponseEntity<Account> signIn(@RequestBody Credentials credentials) {

        //TODO authentication

        Account account = accountService.getAccountByCred(credentials);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping(value = "/sign-up")
    public ResponseEntity<Account> signUp(@RequestBody AccountSignUpDto accountDto) {
        Account account = accountService.register(accountDto);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

}
