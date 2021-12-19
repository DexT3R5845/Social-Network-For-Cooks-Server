package com.edu.netc.bakensweets.repository;

import com.edu.netc.bakensweets.dto.StockIngredientDTO;
import com.edu.netc.bakensweets.model.Ingredient;
import com.edu.netc.bakensweets.model.Stock;
import com.edu.netc.bakensweets.model.form.SearchStockIngredientModel;
import com.edu.netc.bakensweets.repository.interfaces.StockRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class StockRepositoryImpl extends BaseJdbcRepository implements StockRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Value("${sql.stock.create}")
    private String create;
    @Value("${sql.stock.findById}")
    private String findById;
    @Value("${sql.stock.delete}")
    private String delete;
    @Value("${sql.stock.deleteByAccountAndIngredient}")
    private String deleteByAccountAndIngredient;
    @Value("${sql.stock.updateAmountByAccountAndIngredient}")
    private String updateAmountByAccountAndIngredient;
    @Value("${sql.stock.update}")
    private String update;
    @Value("${sql.stock.findAll}")
    private String findAll;
    @Value("${sql.stock.countAll}")
    private String countAll;
    @Value("${sql.stock.findAllViableIngredients}")
    private String findIngredientsToAdd;
    @Value("${sql.stock.countAllViableIngredients}")
    private String countIngredientsToAdd;


    public StockRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate);
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public long create(Stock stock) {
        return jdbcTemplate.queryForObject(create, Long.class, stock.getAccountId(), stock.getIngrId(), stock.getAmount());
    }

    @Override
    public boolean update(Stock stock) {
       return jdbcTemplate.update(update, stock.getAccountId(), stock.getIngrId(), stock.getAmount(), stock.getId()) != 0;
    }

    @Override
    public boolean deleteById(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Stock findById(Long id) {
        return jdbcTemplate.queryForObject(findById, new BeanPropertyRowMapper<>(Stock.class), id);
    }


    @Override
    public Collection<StockIngredientDTO> findAllIngredientsInStock(SearchStockIngredientModel searchStockIngredient) {
        String query = findAll.replace("order", searchStockIngredient.getOrder());
        String sqlQuery = query.replace("sortBy", searchStockIngredient.getSortBy());
        return namedParameterJdbcTemplate.query(sqlQuery, new BeanPropertySqlParameterSource(searchStockIngredient),
                new BeanPropertyRowMapper<>(StockIngredientDTO.class));
    }

    @Override
    public int countAllIngredientsInStock(SearchStockIngredientModel searchStockIngredient) {
        Integer count = namedParameterJdbcTemplate.queryForObject(countAll, new BeanPropertySqlParameterSource(searchStockIngredient), Integer.class);
        return count == null ? 0 : count;
    }

    @Override
    public boolean deleteByAccountAndIngredient(long accountId, long ingredientId) {
        return jdbcTemplate.update(deleteByAccountAndIngredient, accountId, ingredientId) != 0;
    }

    @Override
    public boolean updateAmountByAccountAndIngredient(long accountId, long ingredientId, int amount) {
         return jdbcTemplate.update(updateAmountByAccountAndIngredient, amount, accountId, ingredientId) !=0;
    }


    @Override
    public Collection<Ingredient> findViableIngredients(SearchStockIngredientModel searchStockIngredient) {
        String query = findIngredientsToAdd.replace("order", searchStockIngredient.getOrder());
        String sqlQuery = query.replace("sortBy", searchStockIngredient.getSortBy());
        return namedParameterJdbcTemplate.query(sqlQuery, new BeanPropertySqlParameterSource(searchStockIngredient),
                new BeanPropertyRowMapper<>(Ingredient.class));
    }

    @Override
    public int countViableIngredients(SearchStockIngredientModel searchStockIngredient) {
        Integer count = namedParameterJdbcTemplate.queryForObject(countIngredientsToAdd, new BeanPropertySqlParameterSource(searchStockIngredient), Integer.class);
        return count == null ? 0 : count;
    }
}
