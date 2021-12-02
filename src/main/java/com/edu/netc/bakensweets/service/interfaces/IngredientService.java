package com.edu.netc.bakensweets.service.interfaces;

import com.edu.netc.bakensweets.dto.IngredientDTO;
import com.edu.netc.bakensweets.model.Ingredient;

import java.util.Collection;

public interface IngredientService {
    Collection<Ingredient> getAllIngredients();
    void createIngredient(IngredientDTO ingredientDto);
    Ingredient getIngredientById(Long id);
}
