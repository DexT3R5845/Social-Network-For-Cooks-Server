package com.edu.netc.bakensweets.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KitchenwareDTO {
    @NotNull(message = "id is mandatory")
    @NotBlank(message = "id is mandatory")
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
