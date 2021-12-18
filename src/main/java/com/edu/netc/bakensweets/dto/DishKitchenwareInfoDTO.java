package com.edu.netc.bakensweets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishKitchenwareInfoDTO {
    private String id;
    private String name;
    private String imgUrl;
    private boolean active;
    private String category;
    private int amount;
}
