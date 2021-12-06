package com.edu.netc.bakensweets.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@RequiredArgsConstructor
public class Ingredient {
    @NotNull(message = "ID is mandatory")
    @Min(value = 1, message = "ID must be higher than 0")
    private long id;
    @NotNull(message = "Name is mandatory")
    @Size(min = 4, max = 30, message = "Min length for name - 4. Max - 30.")
    private String name;
    private String imgUrl;
    @NotNull(message = "Category for ingredient is mandatory")
    @Size(min = 4, max = 30, message = "Min length for category ingredient - 4. Max - 30.")
    private String ingredientCategory;
    @NotNull(message = "Status is mandatory")
    private boolean active;
}
