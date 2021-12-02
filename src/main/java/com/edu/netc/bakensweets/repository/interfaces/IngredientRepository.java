package com.edu.netc.bakensweets.repository.interfaces;

import com.edu.netc.bakensweets.model.Ingredient;

import java.util.Collection;

public interface IngredientRepository extends BaseCrudRepository<Ingredient, Long> {
Collection<Ingredient> findAll();
}
