package com.edu.netc.bakensweets.service.interfaces;

import com.edu.netc.bakensweets.dto.*;
import com.edu.netc.bakensweets.model.Kitchenware;

import java.util.Collection;
import java.util.List;

public interface KitchenwareService {
    Collection<String> getAllCategories();
    KitchenwareDTO createKitchenware(KitchenwareDTO kitchenwareDTO);
    KitchenwareDTO updateKitchenware(KitchenwareDTO kitchenwareDTO);
    KitchenwareDTO deleteKitchenware(String id);
    KitchenwareDTO reactivateKitchenware(String id);
    ItemsPerPageDTO<KitchenwareDTO> getFilteredKitchenware(String name, List<Object> args, int limit, boolean order, int currentPage);
}
