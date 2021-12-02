package com.edu.netc.bakensweets.controller;

import com.edu.netc.bakensweets.model.Ingredient;
import com.edu.netc.bakensweets.service.interfaces.IngredientService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RequestMapping("/api/ingredient")
@RestController
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @GetMapping("/")
    public Collection<Ingredient> getAllIngredients() {
        return ingredientService.getAllIngredients();
    }
}
