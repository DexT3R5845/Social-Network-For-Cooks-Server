package com.edu.netc.bakensweets.repository;

import com.edu.netc.bakensweets.model.Credentials;
import com.edu.netc.bakensweets.repository.interfaces.CredentialsRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CredentialsRepositoryImpl extends BaseJdbsRepository implements CredentialsRepository {

    @Value("${sql.credentials.findByEmail}")
    private String sqlQueryFindByEmail;
    @Value("${sql.credentials.findById}")
    private String sqlQueryFindById;

    public CredentialsRepositoryImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void create(Credentials credentials) {

    }

    @Override
    public void update(Credentials credentials) {
        return;
    }
    @Override
    public void deleteById(Long id) {
        //jdbcTemplate.update(sqlQueryDelete, id);
    }
    @Override
    public Credentials findById(Long id) {
        return jdbcTemplate.queryForObject(sqlQueryFindById, new BeanPropertyRowMapper<>(Credentials.class), id);
    }
    @Override
    public Credentials findByEmail(String email) {
        return jdbcTemplate.queryForObject(sqlQueryFindByEmail, new BeanPropertyRowMapper<>(Credentials.class), email);
    }
}
