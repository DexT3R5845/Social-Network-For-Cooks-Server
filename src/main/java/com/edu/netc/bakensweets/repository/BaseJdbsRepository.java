package com.edu.netc.bakensweets.repository;

import org.springframework.jdbc.core.JdbcTemplate;

public class BaseJdbsRepository {
    protected JdbcTemplate jdbcTemplate;

    public BaseJdbsRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
}
