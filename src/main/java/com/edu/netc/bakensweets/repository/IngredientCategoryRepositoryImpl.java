package com.edu.netc.bakensweets.repository;

import com.edu.netc.bakensweets.model.IngredientCategory;
import com.edu.netc.bakensweets.repository.interfaces.IngredientCategoryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class IngredientCategoryRepositoryImpl extends BaseJdbcRepository implements IngredientCategoryRepository {
    @Value("${sql.ingredientCategory.findAll}")
    private String sqlQueryFindAll;

    public IngredientCategoryRepositoryImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void create(IngredientCategory item) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean update(IngredientCategory item) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean deleteById(Long aLong) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IngredientCategory findById(Long id) {
        throw new UnsupportedOperationException();
    }


    @Override
    public Collection<IngredientCategory> findAll() {
        return jdbcTemplate.query(sqlQueryFindAll, new BeanPropertyRowMapper<>(IngredientCategory.class));
    }
}
