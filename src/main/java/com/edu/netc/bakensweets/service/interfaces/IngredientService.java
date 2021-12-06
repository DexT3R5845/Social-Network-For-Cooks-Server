package com.edu.netc.bakensweets.service.interfaces;

import com.edu.netc.bakensweets.dto.IngredientDTO;
import com.edu.netc.bakensweets.dto.PaginationDTO;
import com.edu.netc.bakensweets.model.Ingredient;
import com.edu.netc.bakensweets.model.form.SearchIngredientModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collection;

public interface IngredientService {
    void createIngredient(IngredientDTO ingredientDto);
    Ingredient getIngredientById(Long id);
    PaginationDTO<Ingredient> getAllIngredients(SearchIngredientModel searchIngredientModel);
    void updateIngredient(Ingredient ingredient);
    void updateStatus(Long id, boolean status);
}
