package com.edu.netc.bakensweets.controller;

import com.edu.netc.bakensweets.dto.AccountDTO;
import com.edu.netc.bakensweets.dto.AccountDemoDTO;
import com.edu.netc.bakensweets.service.ModeratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/moderator")
public class ModeratorController {
    private ModeratorService moderatorService;

    @Autowired
    public ModeratorController (ModeratorService moderatorService) {
        this.moderatorService = moderatorService;
    }


    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/new")
    public String addModerator(@RequestBody AccountDTO accountDTO) {
        return moderatorService.createModerator(accountDTO);
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public Map<String, Object> getAllModerators(
            @RequestParam(value = "pageNum", defaultValue = "1", required = false) int currentPage,
            @RequestParam(value = "search", defaultValue = "", required = false) String search) {
        return moderatorService.getAllModerators(search, currentPage);
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public AccountDemoDTO getModerById(@PathVariable long id) {
        return moderatorService.findById(id);
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public AccountDemoDTO updateModerator(@PathVariable long id, @RequestBody AccountDemoDTO accountDTO) {
        return moderatorService.updateModerator(id, accountDTO);
    }
}
