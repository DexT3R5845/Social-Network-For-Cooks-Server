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
        throw new UnsupportedOperationException();
    }

    @Override
    public void changeStatusById(Long id) {
        jdbcTemplate.update(sqlChangeStatus, id);
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
        name = "%" + name + "%";
        String questionMarks;
        boolean filterByCategories = false;
        if (args != null) {
            questionMarks = String.join(",", Collections.nCopies(args.size(), "?"));
            filterByCategories = true;
        }
        else {
            questionMarks = "?";
            args = new ArrayList<Object>();
            args.add(null);
        }
        args.add(!filterByCategories);
        args.add(name);
        args.add(active);
        args.add(limit);
        args.add(offset);
        return jdbcTemplate.query(
                String.format(searchQuery, questionMarks), new BeanPropertyRowMapper<>(Kitchenware.class),
                args.toArray()
        );
    }

    @Override
    public int countFilteredKitchenware (String name, List<Object> args, Boolean active) {
        name = "%" + name + "%";
        String questionMarks;
        boolean filterByCategories = false;
        if (args != null) {
            questionMarks = String.join(",", Collections.nCopies(args.size(), "?"));
            filterByCategories = true;
        }
        else {
            questionMarks = "?";
        }
        Collection<Object> argsCopy = new ArrayList<Object>();
        if (args != null) {
            for (Object obj : args) {
                argsCopy.add(obj);
            }
        }
        else {
            argsCopy.add(null);
        }
        argsCopy.add(!filterByCategories);
        argsCopy.add(name);
        argsCopy.add(active);
        String request = String.format(sqlFilter, questionMarks);
        System.out.println(argsCopy);
        System.out.println(request);
        Integer count = jdbcTemplate.queryForObject(
                request, Integer.class, argsCopy.toArray());
        return count == null ? 0 : count;
    }
}
