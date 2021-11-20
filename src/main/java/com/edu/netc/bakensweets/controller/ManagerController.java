package com.edu.netc.bakensweets.controller;

import com.edu.netc.bakensweets.dto.account.AccountPersonalInfoDTO;
import com.edu.netc.bakensweets.dto.account.AccountsPerPageDTO;
import com.edu.netc.bakensweets.dto.account.NewModeratorDTO;
import com.edu.netc.bakensweets.service.interfaces.AccountService;
import com.edu.netc.bakensweets.service.interfaces.ModerCreationService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    @ApiResponse(code = 409, message = "email is not unique")
    public void addModerator(@RequestBody NewModeratorDTO moderatorDTO) {
        moderCreationService.createToken(moderatorDTO);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    @ApiResponse(code = 400, message = "db/repository error")
    public AccountsPerPageDTO getAllBySearch(
            @RequestParam(value = "size") int size,
            @RequestParam(value = "pageNum", defaultValue = "1", required = false) int currentPage,
            @RequestParam(value = "search", defaultValue = "", required = false) String search) {
        return accountService.getAllBySearchModerators(search, currentPage, size);
    }

     /**
     * RETURNS STATUS INSIDE DTO: TRUE IF USER HAS ROLE_MODER, FALSE - IF ROLE_BAN
     * */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public AccountPersonalInfoDTO getModerById(@PathVariable long id) {
        return accountService.findById(id);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/profile")
    public void updateModerator(@RequestBody AccountPersonalInfoDTO accountDTO) {
        accountService.updatePersonalInfo(accountDTO);
    }

    /**
     * @param status GETS TRUE: CHANGE FROM ROLE_MODER TO ROLE_BAN, FALSE: CHANGE FROM ROLE_BAN TO ROLE_MODER
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/profile-status")
    @ApiResponse(code = 400, message = "no such id in db")
    public void updateStatus(@RequestParam long id, @RequestParam boolean status) {
        accountService.updateModerStatus(id, status);
    }
}