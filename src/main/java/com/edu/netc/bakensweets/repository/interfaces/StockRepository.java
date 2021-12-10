package com.edu.netc.bakensweets.repository.interfaces;

import com.edu.netc.bakensweets.dto.StockIngredientDTO;
import com.edu.netc.bakensweets.model.Account;
import com.edu.netc.bakensweets.model.Stock;
import com.edu.netc.bakensweets.model.form.SearchStockIngredientModel;

import java.util.Collection;

public interface StockRepository extends BaseCrudRepository<Stock, Long> {
    Stock findByAccountAndIngredient(long accountId, long ingredientId);
    Collection<StockIngredientDTO> findAllIngredientsInStock(SearchStockIngredientModel searchStockIngredient);
    int countAllIngredientsInStock(SearchStockIngredientModel searchStockIngredient);
    Collection<Account> findAllAccountsWithStock(int limit, int offset);
    int countAllAccountsWithStock();
}
