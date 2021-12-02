package com.edu.netc.bakensweets.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Ingredient {
    private long id;
    private String name;
    private String imgUrl;
    private String ingredientCategory;
    private boolean active;
}
