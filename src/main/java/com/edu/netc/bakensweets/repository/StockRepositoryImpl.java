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
    private String sqlCreate;
    @Value("${sql.stock.findById}")
    private String sqlFindById;
    @Value("${sql.stock.delete}")
    private String sqlDelete;
    @Value("${sql.stock.deleteByAccountAndIngredient}")
    private String sqlDeleteByAccountAndIngredient;
    @Value("${sql.stock.updateAmountByAccountAndIngredient}")
    private String sqlUpdateAmountByAccountAndIngredient;
    @Value("${sql.stock.update}")
    private String sqlUpdate;
    @Value("${sql.stock.findAll}")
    private String sqlFindAll;
    @Value("${sql.stock.countAll}")
    private String sqlCountAll;
    @Value("${sql.stock.findAllViableIngredients}")
    private String sqlFindIngredientsToAdd;
    @Value("${sql.stock.countAllViableIngredients}")
    private String sqlCountIngredientsToAdd;


    public StockRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate);
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
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
        return true;
    }

    @Override
    public Stock findById(Long id) {
        return jdbcTemplate.queryForObject(sqlFindById, new BeanPropertyRowMapper<>(Stock.class), id);
    }


    @Override
    public Collection<StockIngredientDTO> findAllIngredientsInStock(SearchStockIngredientModel searchStockIngredient) {
        String query = sqlFindAll.replace("order", searchStockIngredient.getOrder());
        String sqlQuery = query.replace("sortBy", searchStockIngredient.getSortBy());
        return namedParameterJdbcTemplate.query(sqlQuery, new BeanPropertySqlParameterSource(searchStockIngredient),
                new BeanPropertyRowMapper<>(StockIngredientDTO.class));
    }

    @Override
    public int countAllIngredientsInStock(SearchStockIngredientModel searchStockIngredient) {
        Integer count = namedParameterJdbcTemplate.queryForObject(sqlCountAll, new BeanPropertySqlParameterSource(searchStockIngredient), Integer.class);
        return count == null ? 0 : count;
    }

    @Override
    public boolean deleteByAccountAndIngredient(long accountId, long ingredientId) {
        return jdbcTemplate.update(sqlDeleteByAccountAndIngredient, accountId, ingredientId) != 0;
    }

    @Override
    public boolean updateAmountByAccountAndIngredient(long accountId, long ingredientId, int amount) {
         return jdbcTemplate.update(sqlUpdateAmountByAccountAndIngredient, amount, accountId, ingredientId) !=0;
    }


    @Override
    public Collection<Ingredient> findViableIngredients(SearchStockIngredientModel searchStockIngredient) {
        String query = sqlFindIngredientsToAdd.replace("order", searchStockIngredient.getOrder());
        String sqlQuery = query.replace("sortBy", searchStockIngredient.getSortBy());
        return namedParameterJdbcTemplate.query(sqlQuery, new BeanPropertySqlParameterSource(searchStockIngredient),
                new BeanPropertyRowMapper<>(Ingredient.class));
    }

    @Override
    public int countViableIngredients(SearchStockIngredientModel searchStockIngredient) {
        Integer count = namedParameterJdbcTemplate.queryForObject(sqlCountIngredientsToAdd, new BeanPropertySqlParameterSource(searchStockIngredient), Integer.class);
        return count == null ? 0 : count;
    }
}