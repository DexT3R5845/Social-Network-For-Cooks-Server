package com.edu.netc.bakensweets.service.interfaces;

import com.edu.netc.bakensweets.dto.*;
import com.edu.netc.bakensweets.model.Kitchenware;

import java.util.Collection;
import java.util.List;

public interface KitchenwareService {
    KitchenwareCategoryCollectionDTO getAllCategories();
    KitchenwareDTO createKitchenware(KitchenwareDTO kitchenwareDTO);
    ItemsPerPageDTO<KitchenwareDTO> getFilteredKitchenware(String name, List<Object> args, int limit, boolean order, int currentPage);
}
