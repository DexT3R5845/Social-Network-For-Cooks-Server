package com.edu.netc.bakensweets.model.form;

import lombok.*;

import javax.validation.constraints.Min;
import java.util.Collection;
import java.util.HashMap;

@Data

public class SearchIngredientModel {
    private Boolean status = null;
    private String searchText = "";
    private Collection<String> ingredientCategory;
    private boolean sortASC = true;
    private String sortBy;
    @Min(value = 1, message = "Min size page is 1")
    private int sizePage = 10;
    @Min(value = 0, message = "Min number page is 0")
    private long numPage = 0;
    @Getter(value=AccessLevel.NONE)
    @Setter(value= AccessLevel.NONE)
    private final HashMap<String, String> keyValueSortBy = new HashMap<String, String>() {
        {
            put("name", "name");
            put("ingredientCategory", "ingredient_category");
            put("active", "active");
            put("id", "id");
        }
    };

    public Collection<String> getIngredientCategory(){
        if(ingredientCategory != null && !ingredientCategory.isEmpty())
            return ingredientCategory;
        return null;
    }

    public String getSearchText(){
        return (searchText == null || searchText.isEmpty()) ? "" : searchText;
    }

    public String getSortBy(){
        return keyValueSortBy.containsKey(sortBy) ? keyValueSortBy.get(sortBy) : "name";
    }

    public SearchIngredientModel(Boolean status, String searchText, Collection<String> ingredientCategory, boolean sortASC, String sortBy, @Min(value = 1, message = "Min size page is 1") int sizePage, @Min(value = 0, message = "Min number page is 0") long numPage) {
        this.status = status;
        this.searchText = searchText;
        this.ingredientCategory = ingredientCategory;
        this.sortASC = sortASC;
        this.sortBy = sortBy;
        this.sizePage = sizePage;
        this.numPage = numPage;
    }
}
