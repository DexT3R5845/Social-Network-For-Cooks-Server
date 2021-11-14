package com.edu.netc.bakensweets.controller;

import com.edu.netc.bakensweets.dto.AccountDTO;
import com.edu.netc.bakensweets.service.ModeratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


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
    public String getAllModerators(
            @RequestParam(value = "search", defaultValue = "", required = false) String search,
            @RequestParam(value = "pageNum", defaultValue = "1", required = false) int currentPage) {
        Map map = moderatorService.getAllModerators(search, currentPage);
        return map == null ? "good" : "bad";
    }
}
