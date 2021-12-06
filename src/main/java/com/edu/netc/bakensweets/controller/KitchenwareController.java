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
import java.util.Collection;
import java.util.List;

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
    public ResponseEntity<Collection<String>> getAllCategories() {
        return ResponseEntity.ok(kitchenwareService.getAllCategories());
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PostMapping(value = "/new")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Kitchenware has been added"),
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 422, message = "Category is invalid")})
    public ResponseEntity<KitchenwareDTO> createKitchenware(@RequestBody KitchenwareDTO kitchenwareDTO) {
        KitchenwareDTO dto = kitchenwareService.createKitchenware(kitchenwareDTO);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PutMapping(value = "/update")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Update successful"),
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 422, message = "Parameter(s) is/are invalid")})
    public ResponseEntity<KitchenwareDTO> updateKitchenware(@RequestBody KitchenwareDTO kitchenwareDTO) {
        KitchenwareDTO dto = kitchenwareService.updateKitchenware(kitchenwareDTO);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @DeleteMapping(value = "/delete/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Deactivation successful"),
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 422, message = "Id is invalid")})
    public ResponseEntity<KitchenwareDTO> deleteKitchenware(@PathVariable String id) {
        KitchenwareDTO dto = kitchenwareService.deleteKitchenware(id);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PutMapping(value = "/reactivate/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Reactivation successful"),
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 422, message = "Id is invalid")})
    public ResponseEntity<KitchenwareDTO> reactivateKitchenware(@PathVariable String id) {
        KitchenwareDTO dto = kitchenwareService.reactivateKitchenware(id);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @GetMapping(value = "/filter")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 422, message = "Invalid params")})
    public ResponseEntity<ItemsPerPageDTO<KitchenwareDTO>> getFilteredKitchenware(
            @RequestParam(value = "pageSize") int pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1", required = false) int currentPage,
            @RequestParam(value = "name", defaultValue = "", required = false) String name,
            @RequestParam(value = "categories", required = false) List<Object> categories,
            @RequestParam(value = "order", defaultValue = "true", required = false) boolean order)
     {
        return ResponseEntity.ok(kitchenwareService.getFilteredKitchenware(name, categories, pageSize, order, currentPage));
    }
}