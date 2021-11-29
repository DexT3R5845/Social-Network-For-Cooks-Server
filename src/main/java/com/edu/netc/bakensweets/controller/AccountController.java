package com.edu.netc.bakensweets.controller;

import com.edu.netc.bakensweets.dto.UpdateAccountDTO;
import com.edu.netc.bakensweets.service.interfaces.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/api/user")
@RestController
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PutMapping(value = "/profile")
    public ResponseEntity<UpdateAccountDTO> updateProfile(@RequestBody UpdateAccountDTO accountDTO, Principal principal) {
        UpdateAccountDTO response = accountService.updateProfile(accountDTO, principal.getName());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "/profile/changePassword")
    public ResponseEntity<String> changePassword(@RequestParam String oldPassword,
                                                 @RequestParam String newPassword,
                                                 Principal principal) {
        String response = accountService.changePassword(oldPassword, newPassword, principal.getName());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/profile")
    public UpdateAccountDTO getUserInfo(Principal principal){
        return accountService.getUserInfoByEmail(principal.getName());
    }
}
