package com.edu.netc.bakensweets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishIngredientDTO {
    @Pattern(regexp = "^[0-9]+$", message = "Id should be numeric")
    private String id;
    private String name;
    private String imgUrl;
    private String ingredientCategory;
    private boolean active;
    private int amount;
}
