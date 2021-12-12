package com.edu.netc.bakensweets.repository;

import com.edu.netc.bakensweets.model.Kitchenware;
import com.edu.netc.bakensweets.repository.interfaces.KitchenwareRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class KitchenwareRepositoryImpl extends BaseJdbcRepository implements KitchenwareRepository {
    public KitchenwareRepositoryImpl(JdbcTemplate jdbcTemplate) {super(jdbcTemplate);}

    @Value("${sql.kitchenware.create}")
    private String sqlCreate;

    @Value("${sql.kitchenware.getAllCategories}")
    private String sqlGetAllCategories;

    @Value("${sql.kitchenware.filter}")
    private String sqlFilter;

    @Value("${sql.kitchenware.filterAsc}")
    private String sqlFilterAsc;

    @Value("${sql.kitchenware.filterDesc}")
    private String sqlFilterDesc;

    @Value("${sql.kitchenware.update}")
    private String sqlUpdate;

    @Value("${sql.kitchenware.changeStatus}")
    private String sqlChangeStatus;

    @Value("${sql.kitchenware.findById}")
    private String sqlFindById;

    private List<Object> filterArgsList;

    private String filterQuestionMarks;


    @Override
    public void create(Kitchenware item) {
        jdbcTemplate.update(sqlCreate, item.getId(), item.getKitchwarName(), item.getKitchwarImg(), item.getKitchwarCategory());
    }

    @Override
    public boolean update(Kitchenware item) {
        int updated = jdbcTemplate.update(sqlUpdate, item.getKitchwarName(), item.getKitchwarImg(), item.getKitchwarCategory(), item.getId());
        if (updated == 1) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean deleteById(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean changeStatusById(Long id) {
        int updated = jdbcTemplate.update(sqlChangeStatus, id);
        if (updated == 1) {
            return true;
        }
        else {
            return false;
        }
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
    public Collection<Kitchenware> filterKitchenware (String name, List<Object> args, Boolean active, int limit, int offset, boolean order) {
        String searchQuery = order ? sqlFilterAsc : sqlFilterDesc;
        createFilterArgsList(name, args, active);
        filterArgsList.add(limit);
        filterArgsList.add(offset);
        System.out.println(filterArgsList);
        return jdbcTemplate.query(
                String.format(searchQuery, filterQuestionMarks), new BeanPropertyRowMapper<>(Kitchenware.class),
                filterArgsList.toArray()
        );
    }

    @Override
    public int countFilteredKitchenware (String name, List<Object> args, Boolean active) {
        createFilterArgsList(name, args, active);
        String request = String.format(sqlFilter, filterQuestionMarks);
        Integer count = jdbcTemplate.queryForObject(
                request, Integer.class, filterArgsList.toArray());
        return count == null ? 0 : count;
    }

    private void createFilterArgsList (String name, List<Object> args, Boolean active) {
        name = "%" + name + "%";
        boolean isFilteredByCategories = false;
        if (args != null) {
            filterArgsList = new ArrayList<>(args);
            if (args.size() > 0) {
                isFilteredByCategories = true;
            }
            else {
                filterArgsList.add("");
            }
            filterQuestionMarks = String.join(",", Collections.nCopies(filterArgsList.size(), "?"));
        }
        else {
            filterQuestionMarks = "?";
            filterArgsList = new ArrayList<>();
            filterArgsList.add("");
        }
        filterArgsList.add(!isFilteredByCategories);
        filterArgsList.add(name);
        filterArgsList.add(active);
    }
}
