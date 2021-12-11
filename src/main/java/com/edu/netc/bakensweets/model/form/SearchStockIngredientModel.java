package com.edu.netc.bakensweets.model.form;


import lombok.*;

import java.util.Collection;

@Data
@AllArgsConstructor
public class SearchStockIngredientModel {
    private long accountId;
    private String search = "";
    private Collection<String> ingredientCategory;
    private String order;
    @Getter(AccessLevel.NONE)
    private String sortBy;
    @Getter(AccessLevel.NONE)
    private Boolean checkCategory;
    private int currentPage;
    private int limit;

    public Boolean getCheckCategory() {
        return getIngredientCategory() == null;
    }

    public Collection<String> getIngredientCategory() {
        return !ingredientCategory.isEmpty() ? ingredientCategory : null;

    }

    public String getSortBy() {
        return sortBy.equals("ingredientCategory") ? "ingredientCategory" : "name";
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
