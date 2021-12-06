package com.edu.netc.bakensweets.controller;

import com.edu.netc.bakensweets.dto.PaginationDTO;
import com.edu.netc.bakensweets.model.Ingredient;
import com.edu.netc.bakensweets.model.form.SearchIngredientModel;
import com.edu.netc.bakensweets.service.interfaces.IngredientService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequestMapping("/api/ingredient")
@RestController
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PostMapping("")
    public PaginationDTO<Ingredient> getAllIngredients(@RequestBody SearchIngredientModel searchIngredientModel) {
        return ingredientService.getAllIngredients(searchIngredientModel);
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PutMapping("/{id}")
    public void updateIngredient(@RequestBody Ingredient ingredient) {
        ingredientService.updateIngredient(ingredient);
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PostMapping("/updateStatus")
    public void updateStatusIngredient(@RequestParam Long id,
                                       @RequestParam boolean status) {
        ingredientService.updateStatus(id, status);
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @GetMapping("/{id}")
    public Ingredient getIngredient(@PathVariable Long id){
        return ingredientService.getIngredientById(id);
    }
}
