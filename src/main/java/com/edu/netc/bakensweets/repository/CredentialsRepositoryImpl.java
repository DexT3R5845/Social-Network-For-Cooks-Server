package com.edu.netc.bakensweets.repository;

import com.edu.netc.bakensweets.model.Credentials;
import com.edu.netc.bakensweets.repository.interfaces.CredentialsRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CredentialsRepositoryImpl extends BaseJdbcRepository implements CredentialsRepository {

    @Value("${sql.credentials.create}")
    private String queryCreate;
    @Value("${sql.credentials.findByEmail}")
    private String queryFindByEmail;
    @Value("${sql.credentials.findById}")
    private String queryFindById;
    @Value("${sql.credentials.update}")
    private String queryUpdate;
    @Value("${sql.credentials.countEmailUsages}")
    private String queryCountEmailUsages;

    public CredentialsRepositoryImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public long create(Credentials credentials) {
        jdbcTemplate.update(queryCreate, credentials.getId(), credentials.getEmail(), credentials.getPassword());
        return 0;
    }

    @Override
    public boolean update(Credentials credentials) {
        jdbcTemplate.update(queryUpdate, credentials.getPassword(), credentials.getEmail());
        return true;
    }

    @Override
    public boolean deleteById(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Credentials findById(Long id) {
        return jdbcTemplate.queryForObject(queryFindById, new BeanPropertyRowMapper<>(Credentials.class), id);
    }

    @Override
    public Credentials findByEmail(String email) {
        return jdbcTemplate.queryForObject(queryFindByEmail, new BeanPropertyRowMapper<>(Credentials.class), email);
    }

    public Integer getCountEmailUsages (String email) {
        return jdbcTemplate.queryForObject(queryCountEmailUsages, Integer.class, email);
    }
}
