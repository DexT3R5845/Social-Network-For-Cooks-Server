package com.edu.netc.bakensweets.repository;

import com.edu.netc.bakensweets.model.Kitchenware;
import com.edu.netc.bakensweets.repository.interfaces.KitchenwareRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class KitchenwareRepositoryImpl extends BaseJdbcRepository implements KitchenwareRepository {
    @Value("${sql.kitchenware.create}")
    private String createRequest;

    @Value("${sql.kitchenware.getAllCategories}")
    private String getAllCategoriesRequest;

    @Value("${sql.kitchenware.filter}")
    private String filterRequest;

    @Value("${sql.kitchenware.getCount}")
    private String getCountRequest;

    @Value("${sql.kitchenware.update}")
    private String updateRequest;

    @Value("${sql.kitchenware.changeStatus}")
    private String changeStatusRequest;

    @Value("${sql.kitchenware.findById}")
    private String findByIdRequest;

    private Map namedParameters;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public KitchenwareRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate);
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public long create(Kitchenware item) {
        return jdbcTemplate.queryForObject(createRequest, Long.class, item.getName(), item.getImgUrl(), item.getCategory());
    }

    @Override
    public boolean update(Kitchenware item) {
        return jdbcTemplate.update(updateRequest, item.getName(), item.getImgUrl(), item.getCategory(), item.getId()) != 0;
    }

    @Override
    public boolean deleteById(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean changeStatusById(Long id) {
        return jdbcTemplate.update(changeStatusRequest, id) != 0;
    }

    @Override
    public Kitchenware findById(Long id) {
        return jdbcTemplate.queryForObject(
                findByIdRequest, new BeanPropertyRowMapper<>(Kitchenware.class), id);
    }

    @Override
    public Collection<String> getAllCategories() {
        return jdbcTemplate.queryForList(
                getAllCategoriesRequest, String.class);
    };

    @Override
    public Collection<Kitchenware> filterKitchenware (String name, Collection<String> categories, Boolean active, int limit, int offset, boolean order) {
        createFilterArgsList(name, categories, active);
        String request = order ? String.format(filterRequest, "ASC", "ASC") : String.format(filterRequest, "DESC", "DESC");
        namedParameters.put("pageSize", limit);
        namedParameters.put("offset", offset);
        return namedParameterJdbcTemplate.query(
                request, namedParameters, new BeanPropertyRowMapper<>(Kitchenware.class)
        );
    }

    @Override
    public int countFilteredKitchenware (String name, Collection<String> categories, Boolean active) {
        createFilterArgsList(name, categories, active);
        Integer count = namedParameterJdbcTemplate.queryForObject(
                getCountRequest, namedParameters, Integer.class);
        return count == null ? 0 : count;
    }

    private void createFilterArgsList (String name, Collection<String> categories, Boolean active) {
        if (categories != null && categories.size() == 0) {
            categories.add(null);
        }
        namedParameters = new HashMap();
        namedParameters.put("categories", categories);
        namedParameters.put("searchText", name);
        namedParameters.put("active", active);
    }
}
