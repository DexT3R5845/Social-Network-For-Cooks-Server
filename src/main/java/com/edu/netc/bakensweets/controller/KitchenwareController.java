package com.edu.netc.bakensweets.controller;

import com.edu.netc.bakensweets.dto.KitchenwareDTO;
import com.edu.netc.bakensweets.dto.PaginationDTO;
import com.edu.netc.bakensweets.service.interfaces.KitchenwareService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Collection;

@Slf4j
@RestController
@Validated
@RequestMapping("/api/kitchenware")
public class KitchenwareController {
    private final KitchenwareService kitchenwareService;

    public KitchenwareController(KitchenwareService kitchenwareService) {
        this.kitchenwareService = kitchenwareService;
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @GetMapping(value = "/categories")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong")})
    public Collection<String> getAllCategories() {
        return kitchenwareService.getAllCategories();
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Kitchenware has been added"),
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 422, message = "Category is invalid")})
    public KitchenwareDTO createKitchenware(@RequestBody @Valid KitchenwareDTO kitchenwareDTO) {
        KitchenwareDTO dto = kitchenwareService.createKitchenware(kitchenwareDTO);
        return dto;
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PutMapping(value = "/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Update successful"),
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 422, message = "Parameter(s) is/are invalid"),
            @ApiResponse(code = 404, message = "Item not found")})
    public KitchenwareDTO updateKitchenware(@PathVariable long id, @RequestBody @Valid KitchenwareDTO kitchenwareDTO) {
        KitchenwareDTO dto = kitchenwareService.updateKitchenware(kitchenwareDTO, id);
        return dto;
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PutMapping(value = "/changeStatus/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Change successful"),
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 404, message = "Item not found")})
    public void changeKitchenwareStatus(@PathVariable long id) {
        kitchenwareService.changeKitchenwareStatus(id);
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @GetMapping(value = "/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 404, message = "Item not found")})
    public KitchenwareDTO getKitchenwareById(@PathVariable long id) {
        return kitchenwareService.getKitchenwareById(id);
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request")})
    public PaginationDTO<KitchenwareDTO> getFilteredKitchenware(
            @RequestParam(value = "pageSize") @Min(value = 1, message = "Page size must be higher than 0") int pageSize,
            @RequestParam(value = "pageNum", defaultValue = "0", required = false) @Min(value = 0, message = "Current page must be higher than 0") int currentPage,
            @RequestParam(value = "name", defaultValue = "", required = false) String name,
            @RequestParam(value = "categories", required = false) Collection<String> categories,
            @RequestParam(value = "active", required = false) Boolean active,
            @RequestParam(value = "order", defaultValue = "true", required = false) boolean order) {
        return kitchenwareService.getFilteredKitchenware(name, categories, active, pageSize, order, currentPage);
    }
}