package com.edu.netc.bakensweets.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
public class DishDTO<I,K> {
    private String dishName;
    @NotNull(message = "Category is mandatory")
    private String dishCategory;
    private String imgUrl;
    private String description;
    private String receipt;
    private String dishType;
    private Collection<K> kitchenwares;
    private Collection<I> ingredients;
}
