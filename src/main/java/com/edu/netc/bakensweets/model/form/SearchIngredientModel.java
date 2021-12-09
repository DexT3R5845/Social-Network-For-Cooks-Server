package com.edu.netc.bakensweets.model.form;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Boolean checkCategory;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Boolean checkStatus;

    public Boolean getCheckCategory(){
        return getIngredientCategory() == null;
    }

    public Boolean getCheckStatus(){
        return getStatus() == null;
    }

    public Collection<String> getIngredientCategory(){
        if(ingredientCategory != null && !ingredientCategory.isEmpty())
            return ingredientCategory;
        return null;
    }

    public String getSearchText(){
        if(searchText == null || searchText.isEmpty())
            searchText = "";
        return "%" + searchText + "%";
    }

    public String getSortBy(){
        return keyValueSortBy.containsKey(sortBy) ? keyValueSortBy.get(sortBy) : "name";
    }
}
