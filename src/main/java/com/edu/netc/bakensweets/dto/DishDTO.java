package com.edu.netc.bakensweets.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

@Data
public class DishDTO {
    @NotNull(message = "ID is mandatory")
    @Min(value = 1, message = "ID must be higher than 0")
    private String id;
    @NotNull(message = "Name is mandatory")
    @Size(min = 3, max = 30, message = "Min length for name - 3. Max - 30.")
    private String dishName;
    @NotNull(message = "Category is mandatory")
    private String dishCategory;
    private String imgUrl;
    private String description;
    private String receipt;
    private String dishType;
    private Collection<DishKitchenwareDTO> kitchenwares;
    private Collection<DishIngredientDTO> ingredients;
}
