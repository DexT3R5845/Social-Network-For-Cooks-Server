package com.edu.netc.bakensweets.model.form;

import lombok.Data;

@Data
public class SearchIngredientModel {
    private String name;
    private String ingredientCategory;
    private boolean filterASC;
    private int sizePage;
    private int numPage;
}
