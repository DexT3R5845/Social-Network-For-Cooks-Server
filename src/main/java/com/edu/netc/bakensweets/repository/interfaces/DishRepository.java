package com.edu.netc.bakensweets.repository.interfaces;

import com.edu.netc.bakensweets.model.Dish;
import com.edu.netc.bakensweets.model.DishIngredient;
import com.edu.netc.bakensweets.model.DishKitchenware;

import java.util.Collection;
import java.util.List;

public interface DishRepository extends BaseCrudRepository<Dish, Long> {
    void createDishIngredient(List<DishIngredient> ingredients, long dish_id);
    void createDishKitchenware(List<DishKitchenware> kitchenwares, long dish_id);
    Collection<DishKitchenware> findKitchenwaresByDishId(Long id);
    Collection<DishIngredient> findIngredientsByDishId(Long id);
    void deleteIngredientsByDishId(long id);
    void deleteKitchenwaresByDishId(long id);
    int countDishesByStock(long id);
    Collection<Dish> getDishesByStock(long id, int limit, int offset);
    int countFilteredDishes(String name, List<String> categories, List<String> ingredients);
    Collection<Dish> findAllDishes(String name, List<String> categories, List<String> ingredients, boolean order, int limit, int offset);
}

