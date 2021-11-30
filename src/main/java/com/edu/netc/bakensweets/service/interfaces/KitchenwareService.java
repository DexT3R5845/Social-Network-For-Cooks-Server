package com.edu.netc.bakensweets.service.interfaces;

import com.edu.netc.bakensweets.dto.KitchenwareCategoryCollectionDTO;
import com.edu.netc.bakensweets.dto.KitchenwareCategoryDTO;
import com.edu.netc.bakensweets.dto.KitchenwareDTO;
import com.edu.netc.bakensweets.model.Kitchenware;

public interface KitchenwareService {
    KitchenwareCategoryCollectionDTO getAllCategories();
    String createKitchenware(KitchenwareDTO kitchenwareDTO);
}
