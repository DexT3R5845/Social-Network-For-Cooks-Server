package com.edu.netc.bakensweets.service;

import com.edu.netc.bakensweets.dto.IngredientDTO;
import com.edu.netc.bakensweets.model.Ingredient;
import com.edu.netc.bakensweets.repository.interfaces.IngredientRepository;
import com.edu.netc.bakensweets.service.interfaces.IngredientService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Collection<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    @Override
    public void createIngredient(IngredientDTO ingredientDto) {

    }

    @Override
    public Ingredient getIngredientById(Long id) {
        return ingredientRepository.findById(id);
    }
}
