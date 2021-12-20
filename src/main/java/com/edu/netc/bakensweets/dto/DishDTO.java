package com.edu.netc.bakensweets.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

@Data
public class DishDTO<I,K> {
    @Size(max = 30, message = "Max length for name 30.")
    private String dishName;
    @NotNull(message = "Category is mandatory")
    @Size(max = 30, message = "Max length for category - 30.")
    private String dishCategory;
    private String imgUrl;
    @Size(max = 1000, message = "Max length for description - 1000.")
    private String description;
    @Size(max = 3000, message = "Max length for receipt - 3000.")
    private String receipt;
    @Size(max = 30, message = "Max - 30.")
    private String dishType;
    private Collection<K> kitchenwares;
    private Collection<I> ingredients;
}
