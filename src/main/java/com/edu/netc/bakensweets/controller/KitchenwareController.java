package com.edu.netc.bakensweets.controller;

import com.edu.netc.bakensweets.dto.*;
import com.edu.netc.bakensweets.service.interfaces.AccountService;
import com.edu.netc.bakensweets.service.interfaces.KitchenwareService;
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
@RequestMapping("/api/kitchenware")
public class KitchenwareController {
    private final KitchenwareService kitchenwareService;

    public KitchenwareController (KitchenwareService kitchenwareService) {
        this.kitchenwareService = kitchenwareService;
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @GetMapping(value = "/categories")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong")})
    public ResponseEntity<KitchenwareCategoryCollectionDTO> getAllCategories() {
        return ResponseEntity.ok(kitchenwareService.getAllCategories());
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PostMapping(value = "/new")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Kitchenware has been added"),
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 422, message = "Category is invalid")})
    public ResponseEntity<String> createKitchenware(@RequestBody KitchenwareDTO kitchenwareDTO) {
        return ResponseEntity.ok(kitchenwareService.createKitchenware(kitchenwareDTO));
    }
}