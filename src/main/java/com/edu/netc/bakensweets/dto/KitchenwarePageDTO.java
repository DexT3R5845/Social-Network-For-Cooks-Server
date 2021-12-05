package com.edu.netc.bakensweets.dto;

import com.edu.netc.bakensweets.model.Account;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

@Data
@AllArgsConstructor
public class KitchenwarePageDTO {
    private Collection<KitchenwareDTO> kitchenwarePage;
    private int currentPage;
    private int pageCount;
}