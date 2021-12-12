package com.edu.netc.bakensweets.model.form;


import lombok.*;

import java.util.Collection;

@Data
public class SearchStockIngredientModel {
    private long accountId;
    private String search = "";
    private Collection<String> ingredientCategory;
    private String order;
    @Getter(AccessLevel.NONE)
    private String sortBy;
    private int currentPage;
    private int limit;


    public Collection<String> getIngredientCategory() {
        if (ingredientCategory != null && !ingredientCategory.isEmpty())
            return ingredientCategory;
        return null;
    }

    public String getSortBy() {
        return sortBy.equals("ingredient_category") ? "ingredient_category" : "name";
    }

    public SearchStockIngredientModel(long accountId, String search, Collection<String> ingredientCategory, String order, String sortBy, int currentPage, int limit) {
        this.accountId = accountId;
        this.search = search;
        this.ingredientCategory = ingredientCategory;
        this.order = order;
        this.sortBy = sortBy;
        this.currentPage = currentPage;
        this.limit = limit;
    }
}
