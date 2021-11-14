package com.edu.netc.bakensweets.repository;

import com.edu.netc.bakensweets.model.Account;
import com.edu.netc.bakensweets.repository.interfaces.ModeratorRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class ModeratorRepositoryImpl extends BaseJdbsRepository implements ModeratorRepository {

    public ModeratorRepositoryImpl (JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Value("sql.moderator.countFindAll")
    private String countFindAll;

    @Value("sql.moderator.findAll")
    private String findAll;



    @Override
    public int getAllModersCount (String search) {
        Integer count = jdbcTemplate.queryForObject(countFindAll, Integer.class, search);
        return count == null ? 0 : count;
    }

    @Override
    public List<Account> getAllModers (String search, int rowOffset) {
        return jdbcTemplate.query(findAll, new BeanPropertyRowMapper<>(Account.class), search, rowOffset);
    }

    @Override
    public void create (Account item) {

    }

    @Override
    public void update (Account item) {

    }

    @Override
    public void deleteById (Long aLong) {

    }

    @Override
    public Account findById (Long aLong) {
        return null;
    }
}
