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
    private int totalLikes;
    private boolean isLiked;
    private boolean isFavorite;

    public boolean getIsLiked () {
        return isLiked;
    }

    public void setIsLiked (boolean liked) {
        isLiked = liked;
    }

    public boolean getIsFavorite () {
        return isFavorite;
    }

    public void setIsFavorite (boolean favorite) {
        isFavorite = favorite;
    }
}
