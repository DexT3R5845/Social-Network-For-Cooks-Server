package com.edu.netc.bakensweets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishIngredientInfoDTO {
    private String id;
    private String name;
    private String imgUrl;
    private String ingredientCategory;
    private boolean active;
    private int amount;
}
