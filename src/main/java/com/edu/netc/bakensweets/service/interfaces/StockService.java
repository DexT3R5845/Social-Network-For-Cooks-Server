package com.edu.netc.bakensweets.service.interfaces;



public interface StockService{
    void addToStock(String accountEmail, long ingredientId, int amount);
    void deleteFromStock(String accountEmail, long ingredientId);
    void updateIngredientFromStock(long accountId, long ingredientId, int amount);
    void deleteFromStock(long accountId, long ingredientId);
}
