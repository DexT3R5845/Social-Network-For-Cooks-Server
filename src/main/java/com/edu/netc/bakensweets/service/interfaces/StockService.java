package com.edu.netc.bakensweets.service.interfaces;


import com.edu.netc.bakensweets.dto.AccountPersonalInfoDTO;
import com.edu.netc.bakensweets.dto.PaginationDTO;
import com.edu.netc.bakensweets.dto.StockIngredientDTO;
import com.edu.netc.bakensweets.model.Ingredient;

import java.util.List;

public interface StockService {
    void addToStock(String accountEmail, long ingredientId, int amount);
    void deleteFromStock(String accountEmail, long ingredientId);
    void updateIngredientFromStock(long accountId, long ingredientId, int amount);
    void deleteFromStock(long accountId, long ingredientId);
    PaginationDTO<StockIngredientDTO> getIngredientsFromStock(int size, int currentPage, String search, boolean order, String sortBy,
                                                              List<String> ingredientCategory, String accountEmail);
    PaginationDTO<AccountPersonalInfoDTO> getAccountsWithStock(int size, int currentPage);
    PaginationDTO<Ingredient> getIngredientsToAdd(int size, int currentPage, String search, boolean order, String sortBy,
                                                  List<String> ingredientCategory, String accountEmail);
}

