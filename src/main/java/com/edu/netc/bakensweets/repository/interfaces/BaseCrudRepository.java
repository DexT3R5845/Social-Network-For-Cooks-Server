package com.edu.netc.bakensweets.repository.interfaces;

public interface BaseCrudRepository<T,ID> {
    void create(T item);
    void update(T item);
    void deleteById(ID id);
    T findById(ID id);
}
