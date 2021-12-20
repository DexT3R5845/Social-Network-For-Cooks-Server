package com.edu.netc.bakensweets.repository;

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
    public long create(String item) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean update(String item) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean deleteById(Long aLong) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String findById(Long aLong) {
        throw new UnsupportedOperationException();
    }


    @Override
    public Collection<String> findAll() {
        return jdbcTemplate.queryForList(sqlQueryFindAll, String.class);
    }
}
