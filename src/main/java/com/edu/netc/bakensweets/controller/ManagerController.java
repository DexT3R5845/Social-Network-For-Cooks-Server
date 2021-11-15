package com.edu.netc.bakensweets.controller;

import com.edu.netc.bakensweets.dto.AccountDTO;
import com.edu.netc.bakensweets.dto.AccountPersonalInfoDTO;
import com.edu.netc.bakensweets.service.ManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/management")
public class ManagerController {
    private ManagerService managerService;

    @Autowired
    public ManagerController (ManagerService managerService) {
        this.managerService = managerService;
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/new")
    public String addModerator(@RequestBody AccountDTO accountDTO) {
        return managerService.createModerator(accountDTO);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public Map<String, Object> getAllModersLimit(
            @RequestParam(value = "size", required = true) int size,
            @RequestParam(value = "pageNum", defaultValue = "1", required = false) int currentPage,
            @RequestParam(value = "search", defaultValue = "", required = false) String search) {
        return managerService.getAllModerators(search, currentPage, size);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
    @GetMapping("/{id}")
    public AccountPersonalInfoDTO getModerById(@PathVariable long id) {
        return managerService.findById(id);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public AccountPersonalInfoDTO updateModerator(@PathVariable long id, @RequestBody AccountPersonalInfoDTO accountDTO) {
        return managerService.updateModerator(id, accountDTO);
    }
}
