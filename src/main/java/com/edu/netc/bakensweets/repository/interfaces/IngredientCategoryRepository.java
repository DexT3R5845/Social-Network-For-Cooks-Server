package com.edu.netc.bakensweets.repository.interfaces;

import com.edu.netc.bakensweets.model.IngredientCategory;

import java.util.Collection;

public interface IngredientCategoryRepository extends BaseCrudRepository<IngredientCategory, Long> {
    Collection<IngredientCategory> findAll();
}
