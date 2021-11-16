package com.edu.netc.bakensweets.controller;

import com.edu.netc.bakensweets.dto.AccountDTO;
import com.edu.netc.bakensweets.dto.AccountPersonalInfoDTO;
import com.edu.netc.bakensweets.dto.AccountsPerPageDTO;
import com.edu.netc.bakensweets.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/management")
public class ManagerController {
    private AccountService accountService;

    @Autowired
    public ManagerController (AccountService accountService) {
        this.accountService = accountService;
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/new")
    public String addModerator(@RequestBody AccountDTO accountDTO) {
        return accountService.createModerator(accountDTO);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public AccountsPerPageDTO getAllBySearch(
            @RequestParam(value = "size") int size,
            @RequestParam(value = "pageNum", defaultValue = "1", required = false) int currentPage,
            @RequestParam(value = "search", defaultValue = "", required = false) String search) {
        return accountService.getAllBySearchModerators(search, currentPage, size);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
    @GetMapping("/{id}")
    public AccountPersonalInfoDTO getModerById(@PathVariable long id) {
        return accountService.findById(id);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public void updateModerator(@PathVariable long id, @RequestBody AccountPersonalInfoDTO accountDTO) {
        accountService.updatePersonalInfo(accountDTO);
    }
}
