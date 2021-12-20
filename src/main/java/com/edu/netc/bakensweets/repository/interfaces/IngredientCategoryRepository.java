package com.edu.netc.bakensweets.repository.interfaces;

import java.util.Collection;

public interface IngredientCategoryRepository extends BaseCrudRepository<String, Long> {
    Collection<String> findAll();
}
