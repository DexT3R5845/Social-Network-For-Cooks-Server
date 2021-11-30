package com.edu.netc.bakensweets.dto;

import com.edu.netc.bakensweets.model.Account;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

@Data
@AllArgsConstructor
public class KitchenwareCategoryCollectionDTO {
    private Collection<KitchenwareCategoryDTO> categories;

}