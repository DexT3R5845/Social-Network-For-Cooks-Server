package com.edu.netc.bakensweets.repository;

import com.edu.netc.bakensweets.model.Stock;
import com.edu.netc.bakensweets.repository.interfaces.StockRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StockRepositoryImpl extends BaseJdbcRepository implements StockRepository {
    @Value("${sql.stock.create}")
    private String sqlCreate;
    @Value("${sql.stock.findById}")
    private String sqlFindById;
    @Value("${sql.stock.delete}")
    private String sqlDelete;
    @Value("${sql.stock.findByAccountAndIngredient}")
    private String sqlFindByAccountAndIngredient;
    @Value("${sql.stock.update}")
    private String sqlUpdate;

    public StockRepositoryImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void create(Stock stock) {
        jdbcTemplate.update(sqlCreate, stock.getAccountId(), stock.getIngrId(), stock.getAmount());
    }

    @Override
    public boolean update(Stock stock) {
        jdbcTemplate.update(sqlUpdate, stock.getAccountId(), stock.getIngrId(), stock.getAmount(), stock.getId());
        return true;
    }

    @Override
    public boolean deleteById(Long id) {
        jdbcTemplate.update(sqlDelete, id);
        System.out.println(jdbcTemplate.update(sqlDelete, id));
        return true;
    }

    @Override
    public Stock findById(Long id) {
        return jdbcTemplate.queryForObject(sqlFindById, new BeanPropertyRowMapper<>(Stock.class), id);
    }

    @Override
    public Stock findByAccountAndIngredient(long accountId, long ingredientId) {
        try {
            return jdbcTemplate.queryForObject(sqlFindByAccountAndIngredient, new BeanPropertyRowMapper<>(Stock.class), accountId,
                    ingredientId);
        } catch (EmptyResultDataAccessException ex){
            return null;
        }
    }
}
