package com.edu.netc.bakensweets.repository;

import com.edu.netc.bakensweets.model.Account;
import com.edu.netc.bakensweets.model.Ingredient;
import com.edu.netc.bakensweets.repository.interfaces.IngredientRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class IngredientRepositoryImpl extends BaseJdbsRepository implements IngredientRepository {
    @Value("${sql.ingredient.create}")
    private String sqlQueryCreate;
    @Value("${sql.ingredient.findById}")
    private String sqlQueryFindById;
    @Value("${sql.ingredient.findAll}")
    private String sqlQueryFindAll;

    public IngredientRepositoryImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void create(Ingredient ingredient) {
        jdbcTemplate.update(sqlQueryCreate, ingredient.getName(), ingredient.getImgUrl(), ingredient.getIngredientCategory(),
                ingredient.isActive());
    }

    @Override
    public void update(Ingredient item) {

    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public Ingredient findById(Long id) {
        return jdbcTemplate.queryForObject(sqlQueryFindById, new BeanPropertyRowMapper<>(Ingredient.class), id);
    }

    @Override
    public Collection<Ingredient> findAll() {
        return jdbcTemplate.query(sqlQueryFindAll, new BeanPropertyRowMapper<>(Ingredient.class));
    }
}
