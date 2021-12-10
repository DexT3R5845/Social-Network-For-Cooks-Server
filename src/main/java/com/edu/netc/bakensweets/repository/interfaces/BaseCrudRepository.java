package com.edu.netc.bakensweets.repository.interfaces;

import java.util.Collection;

public interface BaseCrudRepository<T,ID> {
    void create(T item);
    boolean update(T item);
    boolean deleteById(ID id);
    T findById(ID id);
}
