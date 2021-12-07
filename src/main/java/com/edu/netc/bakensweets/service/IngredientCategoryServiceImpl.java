package com.edu.netc.bakensweets.service;

import com.edu.netc.bakensweets.model.IngredientCategory;
import com.edu.netc.bakensweets.repository.interfaces.IngredientCategoryRepository;
import com.edu.netc.bakensweets.service.interfaces.IngredientCategoryService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class IngredientCategoryServiceImpl implements IngredientCategoryService {
    private final IngredientCategoryRepository ingredientCategoryRepository;

    public IngredientCategoryServiceImpl(IngredientCategoryRepository ingredientCategoryRepository){
        this.ingredientCategoryRepository = ingredientCategoryRepository;
    }
    @Override
    public Collection<IngredientCategory> getAllCategory() {
        return ingredientCategoryRepository.findAll();
    }
}
