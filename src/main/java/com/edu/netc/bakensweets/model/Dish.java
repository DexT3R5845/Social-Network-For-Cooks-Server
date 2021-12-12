package com.edu.netc.bakensweets.model;

import lombok.Data;

@Data
public class Dish {
    private long id;
    private String dishName;
    private String dishCategory;
    private String imgUrl;
    private String description;
    private String receipt;
    private String dishType;
}
