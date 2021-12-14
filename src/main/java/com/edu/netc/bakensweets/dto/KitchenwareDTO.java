package com.edu.netc.bakensweets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KitchenwareDTO {
    @Pattern(regexp = "[0-9]+", message = "id should be numeric")
    private String id;
    @NotNull(message = "name is mandatory")
    @NotBlank(message = "name is mandatory")
    private String name;
    private String imgUrl;
    private boolean active;
    @NotNull(message = "category is mandatory")
    @NotBlank(message = "category is mandatory")
    private String category;
}
