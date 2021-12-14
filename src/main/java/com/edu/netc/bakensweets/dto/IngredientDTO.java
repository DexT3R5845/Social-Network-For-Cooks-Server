package com.edu.netc.bakensweets.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class IngredientDTO {
    @NotNull(message = "Name is mandatory")
    @Size(min = 4, max = 30, message = "Min length for name - 4. Max - 30.")
    private String name;
    @Size(max=300, message = "Max size image url is 300 symbols.")
    private String imgUrl;
    @NotEmpty(message = "Category for ingredient is mandatory")
    private String ingredientCategory;
    private boolean active = true;
}
