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

import java.util.Collection;

@Repository
public class KitchenwareRepositoryImpl extends BaseJdbsRepository implements KitchenwareRepository {
    public KitchenwareRepositoryImpl(JdbcTemplate jdbcTemplate) {super(jdbcTemplate);}

    @Value("${sql.kitchenware.create}")
    private String sqlCreate;

    @Value("${sql.kitchenware.getAllCategories}")
    private String sqlGetAllCategories;

    @Override
    public void create(Kitchenware kitchenware) {
        jdbcTemplate.update(sqlCreate, kitchenware.getKitchwarName(), kitchenware.getKitchwarImg(), kitchenware.getKitchwarCategory());
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
}
