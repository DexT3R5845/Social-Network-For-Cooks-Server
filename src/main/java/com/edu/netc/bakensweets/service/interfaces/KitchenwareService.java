package com.edu.netc.bakensweets.service.interfaces;

import com.edu.netc.bakensweets.dto.KitchenwareDTO;
import com.edu.netc.bakensweets.dto.PaginationDTO;

import java.util.Collection;

public interface KitchenwareService {
    Collection<String> getAllCategories();

    KitchenwareDTO createKitchenware(KitchenwareDTO kitchenwareDTO);

    KitchenwareDTO updateKitchenware(KitchenwareDTO kitchenwareDTO, long id);

    void changeKitchenwareStatus(long id);

    KitchenwareDTO getKitchenwareById(Long id);

    PaginationDTO<KitchenwareDTO> getFilteredKitchenware(String name, Collection<String> args, Boolean active, int limit, boolean order, int currentPage);
}
