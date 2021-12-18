package com.edu.netc.bakensweets.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class DishInfoDTO<I,K> {
    private String id;
    private String dishName;
    private String dishCategory;
    private String imgUrl;
    private String description;
    private String receipt;
    private String dishType;
    private int totalLikes;
    private boolean isLiked;
    private boolean isFavorite;
    private Collection<K> kitchenwares;
    private Collection<I> ingredients;
}
