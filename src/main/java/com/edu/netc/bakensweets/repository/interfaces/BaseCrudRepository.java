package com.edu.netc.bakensweets.repository.interfaces;

import java.util.Collection;

public interface BaseCrudRepository<T,ID> {
    long create(T item);
    boolean update(T item);
    boolean deleteById(ID id);
    T findById(ID id);
}
