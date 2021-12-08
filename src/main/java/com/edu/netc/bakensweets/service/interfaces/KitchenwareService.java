package com.edu.netc.bakensweets.service.interfaces;

import com.edu.netc.bakensweets.dto.*;
import com.edu.netc.bakensweets.model.Kitchenware;

import java.util.Collection;
import java.util.List;

public interface KitchenwareService {
    Collection<String> getAllCategories();
    KitchenwareDTO createKitchenware(KitchenwareDTO kitchenwareDTO);
    KitchenwareDTO updateKitchenware(KitchenwareDTO kitchenwareDTO, long id);
    void changeKitchenwareStatus(long id);
    KitchenwareDTO getKitchenwareById(Long id);
    ItemsPerPageDTO<KitchenwareDTO> getFilteredKitchenware(String name, List<Object> args, Boolean active, int limit, boolean order, int currentPage);
}
