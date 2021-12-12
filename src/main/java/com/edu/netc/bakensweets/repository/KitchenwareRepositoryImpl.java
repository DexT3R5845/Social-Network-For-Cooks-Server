package com.edu.netc.bakensweets.repository;

import com.edu.netc.bakensweets.model.Account;
import com.edu.netc.bakensweets.model.Friendship;
import com.edu.netc.bakensweets.model.Kitchenware;
import com.edu.netc.bakensweets.model.KitchenwareCategory;
import com.edu.netc.bakensweets.repository.interfaces.FriendshipRepository;
import com.edu.netc.bakensweets.repository.interfaces.KitchenwareRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class KitchenwareRepositoryImpl extends BaseJdbcRepository implements KitchenwareRepository {
    @Value("${sql.kitchenware.create}")
    private String sqlCreate;

    @Value("${sql.kitchenware.getAllCategories}")
    private String sqlGetAllCategories;

    @Value("${sql.kitchenware.filter}")
    private String sqlFilter;

    @Value("${sql.kitchenware.getCount}")
    private String sqlGetCount;

    @Value("${sql.kitchenware.update}")
    private String sqlUpdate;

    @Value("${sql.kitchenware.changeStatus}")
    private String sqlChangeStatus;

    @Value("${sql.kitchenware.findById}")
    private String sqlFindById;

    private Map namedParameters;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public KitchenwareRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate);
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public long create(Kitchenware item) {
        return jdbcTemplate.queryForObject(sqlCreate, Integer.class, item.getKitchwarName(), item.getKitchwarImg(), item.getKitchwarCategory());
    }

    @Override
    public boolean update(Kitchenware item) {
        int updated = jdbcTemplate.update(sqlUpdate, item.getKitchwarName(), item.getKitchwarImg(), item.getKitchwarCategory(), item.getId());
        return updated != 0;
    }

    @Override
    public boolean deleteById(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean changeStatusById(Long id) {
        int updated = jdbcTemplate.update(sqlChangeStatus, id);
        return updated != 0;
    }

    @Override
    public Kitchenware findById(Long id) {
        return jdbcTemplate.queryForObject(
                sqlFindById, new BeanPropertyRowMapper<>(Kitchenware.class), id);
    }

    @Override
    public Collection<String> getAllCategories() {
        return jdbcTemplate.queryForList(
                sqlGetAllCategories, String.class);
    };

    @Override
    public Collection<Kitchenware> filterKitchenware (String name, Collection<String> args, Boolean active, int limit, int offset, boolean order) {
        createFilterArgsList(name, args, active);
        String request = order ? String.format(sqlFilter, "ASC", "ASC") : String.format(sqlFilter, "DESC", "DESC");
        namedParameters.put("pageSize", limit);
        namedParameters.put("offset", offset);
        return namedParameterJdbcTemplate.query(
                request, namedParameters, new BeanPropertyRowMapper<>(Kitchenware.class)
        );
    }

    @Override
    public int countFilteredKitchenware (String name, Collection<String> args, Boolean active) {
        createFilterArgsList(name, args, active);
        Integer count = namedParameterJdbcTemplate.queryForObject(
                sqlGetCount, namedParameters, Integer.class);
        return count == null ? 0 : count;
    }

    private void createFilterArgsList (String name, Collection<String> args, Boolean active) {
        if (args != null && args.size() == 0) {
            args.add("");
        }
        namedParameters = new HashMap();
        namedParameters.put("categories", args);
        namedParameters.put("searchText", "%" + name + "%");
        namedParameters.put("active", active);
    }
}
