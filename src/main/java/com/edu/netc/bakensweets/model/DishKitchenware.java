package com.edu.netc.bakensweets.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishKitchenware {
    @Pattern(regexp = "^[0-9]+$", message = "Id should be numeric")
    private long id;
    private String kitchwarName;
    private String kitchwarImg;
    private String kitchwarCategory;
    private boolean active;
    private int amount;
}
