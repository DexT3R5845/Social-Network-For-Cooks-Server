package com.edu.netc.bakensweets.controller;

import com.edu.netc.bakensweets.dto.IngredientDTO;
import com.edu.netc.bakensweets.dto.PaginationDTO;
import com.edu.netc.bakensweets.model.Ingredient;
import com.edu.netc.bakensweets.model.IngredientCategory;
import com.edu.netc.bakensweets.model.form.SearchIngredientModel;
import com.edu.netc.bakensweets.service.interfaces.IngredientCategoryService;
import com.edu.netc.bakensweets.service.interfaces.IngredientService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Validated
@RequestMapping("/api/ingredient")
@RestController
public class IngredientController {
    private final IngredientService ingredientService;
    private final IngredientCategoryService ingredientCategoryService;

    public IngredientController(IngredientService ingredientService, IngredientCategoryService ingredientCategoryService){
        this.ingredientService = ingredientService;
        this.ingredientCategoryService = ingredientCategoryService;
    }

    @PreAuthorize("hasAnyRole('ROLE_MODERATOR', 'ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping("")
    public PaginationDTO<Ingredient> getAllIngredients(@Valid @RequestBody SearchIngredientModel searchIngredientModel) {
        return ingredientService.getAllIngredients(searchIngredientModel);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong")})
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PutMapping("/{id}")
    public void updateIngredient(@Valid @RequestBody IngredientDTO ingredientDto,
                                 @NotNull(message = "ID is mandatory")
                                 @Min(value = 1, message = "ID must be higher than 0") @PathVariable Long id) {
        ingredientService.updateIngredient(ingredientDto, id);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 404, message = "Ingredient not found")})
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PatchMapping("/{id}")
    public void updateStatusIngredient(@NotNull(message = "ID is mandatory") @Min(value = 1, message = "ID must be higher than 0") @PathVariable Long id,
                                       @NotNull(message = "Status is mandatory") @RequestParam boolean status) {
        ingredientService.updateStatus(id, status);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 404, message = "Ingredient not found")})
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @GetMapping("/{id}")
    public Ingredient getIngredient(@NotNull(message = "ID is mandatory") @Min(value = 1, message = "ID must be higher than 0") @PathVariable Long id) {
        return ingredientService.getIngredientById(id);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong")})
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PostMapping("/create")
    public void createIngredient(@Valid @RequestBody IngredientDTO ingredientDTO) {
        ingredientService.createIngredient(ingredientDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_MODERATOR', 'ROLE_USER')")
    @GetMapping("/category")
    public Collection<IngredientCategory> getAllCategory(){
        return ingredientCategoryService.getAllCategory();
    }
}
