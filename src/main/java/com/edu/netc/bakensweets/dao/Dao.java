package com.edu.netc.bakensweets.dao;

import java.sql.SQLException;
import java.util.HashSet;

public interface Dao<E, I> {

    boolean create(E entity) throws SQLException;

    HashSet<E> getAll();

    E getByID(I i);

    boolean update(E entity);

    boolean delete(I i);

}
