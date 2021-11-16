package com.edu.netc.bakensweets.controller;

import com.edu.netc.bakensweets.dto.AccountDTO;
import com.edu.netc.bakensweets.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "accounts")
@RequestMapping("/api")
@RestController
public class AccountController {
    private final AccountService accountService;

    public AccountController (AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/signin")
    public String signIn(@RequestParam String email,
                         @RequestParam String password) {
       return accountService.signIn(email,password);
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<String> signUp(@Validated @RequestBody AccountDTO accountDTO) {

        return ResponseEntity.ok(accountService.signUp(accountDTO));
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${AccountController.test}", authorizations = { @Authorization(value="Authorization") })
    @GetMapping("/test")
    public String test(){
        return "hello";
    }
}
