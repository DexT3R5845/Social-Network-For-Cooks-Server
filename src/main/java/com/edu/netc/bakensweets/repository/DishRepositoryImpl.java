package com.edu.netc.bakensweets.repository;

import com.edu.netc.bakensweets.model.Dish;
import com.edu.netc.bakensweets.model.DishIngredient;
import com.edu.netc.bakensweets.model.DishKitchenware;
import com.edu.netc.bakensweets.repository.interfaces.DishRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Repository
public class DishRepositoryImpl extends BaseJdbcRepository implements DishRepository {
    @Value("${sql.dish.createDish}")
    private String createDish;
    @Value("${sql.dish.createDishIngredient}")
    private String createDishIngredient;
    @Value("${sql.dish.createDishKitchenware}")
    private String createDishKitchenware;

    @Value("${sql.dish.getDishById}")
    private String getDishById;
    @Value("${sql.dish.getIngredientsByDishId}")
    private String getIngredientsByDishId;
    @Value("${sql.dish.getKitchenwaresByDishId}")
    private String getKitchenwaresByDishId;

    @Value("${sql.dish.deleteById}")
    private String deleteById;
    @Value("${sql.dish.deleteIngredientsByDishId}")
    private String deleteIngredientsByDishId;
    @Value("${sql.dish.deleteKitchenwaresByDishId}")
    private String deleteKitchenwaresByDishId;

    @Value("${sql.dish.updateDish}")
    private String updateDish;

    @Value("${sql.dish.countAll}")
    private String countAll;
    @Value("${sql.dish.findAll}")
    private String findAll;

    public DishRepositoryImpl (JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void create (Dish dish) {
        this.jdbcTemplate.update(
                createDish, dish.getId(), dish.getDishName(), dish.getDishCategory(),
                dish.getImgUrl(), dish.getDescription(), dish.getReceipt(), dish.getDishType()
        );
    }

    @Override
    public void createDishIngredient(List<DishIngredient> ingredients, long dish_id) throws DataAccessException {
        this.jdbcTemplate.batchUpdate(createDishIngredient, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, ingredients.get(i).getAmount());
                ps.setLong(2, ingredients.get(i).getId());
                ps.setLong(3, dish_id);
            }

            @Override
            public int getBatchSize() {
                return ingredients.size();
            }
        });
    }

    @Override
    public void createDishKitchenware(List<DishKitchenware> kitchenwares, long dish_id) throws DataAccessException {
        this.jdbcTemplate.batchUpdate(createDishKitchenware, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, kitchenwares.get(i).getAmount());
                ps.setLong(2, kitchenwares.get(i).getId());
                ps.setLong(3, dish_id);
            }

            @Override
            public int getBatchSize() {
                return kitchenwares.size();
            }
        });
    }

    @Override
    public boolean update (Dish dish) {
        return this.jdbcTemplate.update(updateDish, dish.getDishName(), dish.getDishCategory(), dish.getImgUrl(),
                dish.getDescription(), dish.getReceipt(), dish.getDishType(), dish.getId()) != 0;
    }

    @Override
    public boolean deleteById (Long id) {
        return this.jdbcTemplate.update(deleteById, id) != 0;
    }

    @Override
    public void deleteIngredientsByDishId(long id) {
        this.jdbcTemplate.update(deleteIngredientsByDishId, id);
    }

    @Override
    public void deleteKitchenwaresByDishId(long id) {
        this.jdbcTemplate.update(deleteKitchenwaresByDishId, id);
    }

    @Override
    public Dish findById (Long id) {
        return this.jdbcTemplate.queryForObject(getDishById, new BeanPropertyRowMapper<>(Dish.class), id);
    }

    @Override
    public Collection<DishKitchenware> findKitchenwaresByDishId(Long id) {
        return this.jdbcTemplate.query(getKitchenwaresByDishId, new BeanPropertyRowMapper<>(DishKitchenware.class), id);
    }

    @Override
    public Collection<DishIngredient> findIngredientsByDishId(Long id) {
        return this.jdbcTemplate.query(getIngredientsByDishId, new BeanPropertyRowMapper<>(DishIngredient.class), id);
    }

    @Override
    public int countFilteredDishes(String name, List<String> categories, List<String> ingredients) {
        boolean checkCategories = categories == null || categories.isEmpty();
        boolean checkIngredients = ingredients == null || ingredients.isEmpty();

        String query = String.format(countAll, turnListToString(categories), turnListIdToString(ingredients));

        return this.jdbcTemplate.queryForObject(query, Integer.class, name, name, checkCategories, checkIngredients);
    }

    @Override
    public Collection<Dish> findAllDishes(String name, List<String> categories, List<String> ingredients, boolean order, int limit, int offset) {
        boolean checkCategories = categories == null || categories.isEmpty();
        boolean checkIngredients = ingredients == null || ingredients.isEmpty();

        String query = String.format(findAll, turnListToString(categories), turnListIdToString(ingredients), order ? "ASC" : "DESC");

        return this.jdbcTemplate.query(
                query,
                new BeanPropertyRowMapper<>(Dish.class),
                name, name, checkCategories, checkIngredients, limit, offset
        );
    }

    /**
     * CONVERTS LIST TO ROW OF STRINGS LIKE "'string1', 'string2'"
     */
    private String turnListToString (List<String> list) {
        if (list == null || list.size() == 0) {
            return "''";
        }
        StringBuilder sb = new StringBuilder();
        list.forEach(item -> sb.append("'").append(item).append("', "));
        String output = sb.toString();
        return output.substring(0, output.length() - 2);
    }

    /**
     * CONVERTS LIST TO STRING LIKE "number1, number2"
     */
    private String turnListIdToString (List<String> list) {
        if (list == null || list.size() == 0) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        list.forEach(item -> sb.append(item).append(", "));
        String output = sb.toString();
        return output.substring(0, output.length() - 2);
    }
}
