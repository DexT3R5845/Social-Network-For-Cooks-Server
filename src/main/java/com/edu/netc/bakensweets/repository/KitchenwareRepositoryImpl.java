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
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class KitchenwareRepositoryImpl extends BaseJdbsRepository implements KitchenwareRepository {
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

    @Value("${sql.kitchenware.reactivate}")
    private String sqlReactivate;

    @Value("${sql.kitchenware.delete}")
    private String sqlDelete;

    @Value("${sql.kitchenware.findById}")
    private String sqlFindById;


    @Override
    public void create(Kitchenware item) {
        jdbcTemplate.update(sqlCreate, item.getId(), item.getKitchwarName(), item.getKitchwarImg(), item.getKitchwarCategory());
    }

    @Override
    public void update(Kitchenware item) {
        jdbcTemplate.update(sqlUpdate, item.getKitchwarName(), item.getKitchwarImg(), item.getKitchwarCategory(), item.getId());
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update(sqlDelete, id);
    }

    @Override
    public void reactivateById(Long id) {
        jdbcTemplate.update(sqlReactivate, id);
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
    public Collection<Kitchenware> filterKitchenware (String name, List<Object> args, int limit, int offset, boolean order) {
        String searchQuery = order ? sqlFilterAsc : sqlFilterDesc;
        name = "%" + name + "%";
        String questionMarks = String.join(",", Collections.nCopies(args.size(), "?"));
        args.add(name);
        args.add(limit);
        args.add(offset);
        return jdbcTemplate.query(
                String.format(searchQuery, questionMarks), new BeanPropertyRowMapper<>(Kitchenware.class),
                args.toArray()
        );
    }

    @Override
    public int countFilteredKitchenware (String name, List<Object> args) {
        String questionMarks = String.join(",", Collections.nCopies(args.size(), "?"));
        name = "%" + name + "%";
        Collection<Object> argsCopy = new ArrayList<Object>(args);
        argsCopy.add(name);
        String request = String.format(sqlFilter, questionMarks);
        Integer count = jdbcTemplate.queryForObject(
                request, Integer.class, argsCopy.toArray());
        return count == null ? 0 : count;
    }
}
