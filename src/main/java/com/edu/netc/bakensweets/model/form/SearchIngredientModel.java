package com.edu.netc.bakensweets.model.form;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.Min;
import java.util.Collection;
import java.util.HashMap;

@Data
public class SearchIngredientModel {
    private String searchText = "";
    private Collection<String> ingredientCategory;
    private boolean sortASC = true;
    private String sortBy;
    @Min(value = 1, message = "Min size page is 1")
    private int sizePage = 10;
    @Min(value = 0, message = "Min number page is 0")
    private long numPage = 0;

    public Collection<String> getIngredientCategory(){
        if(ingredientCategory != null && !ingredientCategory.isEmpty())
            return ingredientCategory;
        return null;
    }

    public String getSearchText(){
        return "%" + searchText + "%";
    }

    public String getSortBy(){
        HashMap<String, String> keyValueSortBy = new HashMap<String, String>(){{
            put("name", "name");
            put("ingredientCategory", "ingredient_category");
            put("active", "active");
            put("id", "id");
        }};
        return keyValueSortBy.containsKey(sortBy) ? keyValueSortBy.get(sortBy) : "id";
    }
}
