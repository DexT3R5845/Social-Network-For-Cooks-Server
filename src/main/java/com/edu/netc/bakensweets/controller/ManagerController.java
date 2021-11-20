package com.edu.netc.bakensweets.controller;

import com.edu.netc.bakensweets.dto.account.AccountPersonalInfoDTO;
import com.edu.netc.bakensweets.dto.account.AccountsPerPageDTO;
import com.edu.netc.bakensweets.dto.account.NewModeratorDTO;
import com.edu.netc.bakensweets.service.interfaces.AccountService;
import com.edu.netc.bakensweets.service.interfaces.ModerCreationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/management")
public class ManagerController {
    private final AccountService accountService;
    private final ModerCreationService moderCreationService;

    public ManagerController (AccountService accountService, ModerCreationService moderCreationService) {
        this.accountService = accountService;
        this.moderCreationService = moderCreationService;
    }



    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/new")
    public void addModerator(@RequestBody NewModeratorDTO moderatorDTO) {
        moderCreationService.createToken(moderatorDTO);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public AccountsPerPageDTO getAllBySearch(
            @RequestParam(value = "size") int size,
            @RequestParam(value = "pageNum", defaultValue = "1", required = false) int currentPage,
            @RequestParam(value = "search", defaultValue = "", required = false) String search) {
        return accountService.getAllBySearchModerators(search, currentPage, size);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
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