package com.edu.netc.bakensweets.dto;

import lombok.Data;


@Data
public class StockIngredientDTO {
    private long id;
    private String name;
    private String imgUrl;
    private String ingredientCategory;
    private int amount;
}
