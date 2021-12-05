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

    @Override
    public void create(Kitchenware kitchenware) {
        jdbcTemplate.update(sqlCreate, kitchenware.getId(), kitchenware.getKitchwarName(), kitchenware.getKitchwarImg(), kitchenware.getKitchwarCategory());
    }

    @Override
    public void update(Kitchenware item) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Kitchenware findById(Long integer) {
        throw new UnsupportedOperationException();
        // return null;
    }

    @Override
    public Collection<KitchenwareCategory> getAllCategories() {
        return jdbcTemplate.query(
                sqlGetAllCategories, new BeanPropertyRowMapper<>(KitchenwareCategory.class));
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
