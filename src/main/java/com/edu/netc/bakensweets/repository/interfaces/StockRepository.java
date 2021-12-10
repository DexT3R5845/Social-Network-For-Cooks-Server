package com.edu.netc.bakensweets.repository.interfaces;

import com.edu.netc.bakensweets.model.Friendship;
import com.edu.netc.bakensweets.model.Stock;

public interface StockRepository extends BaseCrudRepository<Stock, Long>  {
    Stock findByAccountAndIngredient(long accountId, long ingredientId);
}
