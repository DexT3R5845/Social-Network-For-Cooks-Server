package com.edu.netc.bakensweets.repository.interfaces;

import com.edu.netc.bakensweets.dto.StockIngredientDTO;
import com.edu.netc.bakensweets.model.Account;
import com.edu.netc.bakensweets.model.Ingredient;
import com.edu.netc.bakensweets.model.Stock;
import com.edu.netc.bakensweets.model.form.SearchStockIngredientModel;

import java.util.Collection;

public interface StockRepository extends BaseCrudRepository<Stock, Long> {
    Collection<StockIngredientDTO> findAllIngredientsInStock(SearchStockIngredientModel searchStockIngredient);
    int countAllIngredientsInStock(SearchStockIngredientModel searchStockIngredient);
    boolean deleteByAccountAndIngredient(long accountId, long ingredientId);
    boolean updateAmountByAccountAndIngredient(long accountId, long ingredientId, int amount);
    Collection<Ingredient> findViableIngredients(SearchStockIngredientModel searchStockIngredientModel);
    int countViableIngredients(SearchStockIngredientModel searchStockIngredientModel);
}
