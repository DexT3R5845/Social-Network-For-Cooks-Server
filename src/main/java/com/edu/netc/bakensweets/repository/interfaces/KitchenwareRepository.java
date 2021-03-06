package com.edu.netc.bakensweets.repository.interfaces;

import com.edu.netc.bakensweets.model.Kitchenware;

import java.util.Collection;

public interface KitchenwareRepository extends BaseCrudRepository<Kitchenware, Long> {
    Collection<String> getAllCategories();

    Collection<Kitchenware> filterKitchenware(String name, Collection<String> args, Boolean active, int limit, int offset, boolean order);

    int countFilteredKitchenware(String name, Collection<String> args, Boolean active);

    boolean changeStatusById(Long id);
}
