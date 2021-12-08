package com.edu.netc.bakensweets.controller;

import com.edu.netc.bakensweets.dto.AccountPersonalInfoDTO;
import com.edu.netc.bakensweets.dto.PageDTO;
import com.edu.netc.bakensweets.dto.NewModeratorDTO;
import com.edu.netc.bakensweets.service.interfaces.AccountService;
import com.edu.netc.bakensweets.service.interfaces.ModerCreationService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    @PostMapping("")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "the link has been sent to your email"),
        @ApiResponse(code = 403, message = "this email has actual link that is valid until *time*"),
        @ApiResponse(code = 409, message = "email in not unique")})
    public ResponseEntity<String> addModerator(@Valid @RequestBody NewModeratorDTO moderatorDTO) {
        return ResponseEntity.ok(moderCreationService.createToken(moderatorDTO));
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("")
    public PageDTO getAllBySearch(
            @RequestParam(value = "size") int size,
            @RequestParam(value = "pageNum", defaultValue = "0", required = false) int currentPage,
            @RequestParam(value = "search", defaultValue = "", required = false) String search,
            @RequestParam(value = "order", defaultValue = "true", required = false) boolean order,
            @RequestParam(value = "gender", defaultValue = "", required = false) String gender,
            @RequestParam(value = "status", defaultValue = "", required = false) String status){
        return accountService.getAllBySearchModerators(search, currentPage, size, order, gender, status);
    }

     /**
     * RETURNS STATUS INSIDE DTO: TRUE IF USER HAS ROLE_MODER, FALSE - IF ROLE_BAN
     * */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    @ApiResponse(code = 404, message = "There is no account with such id")
    public AccountPersonalInfoDTO getModerById(@PathVariable long id) {
        return accountService.findById(id);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("")
    @ApiResponse(code = 404, message = "There is no account with such id")
    public void updateModerator(@RequestBody AccountPersonalInfoDTO accountDTO) {
        accountService.updatePersonalInfo(accountDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @ApiResponse(code = 404, message = "There is no account with such id")
    public HttpStatus updateStatus(@PathVariable long id) {
        accountService.updateModerStatus(id);
        return HttpStatus.OK;
    }
}
