package com.edu.netc.bakensweets.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;

public class BaseJdbsRepository {
    protected JdbcTemplate jdbcTemplate;

    public BaseJdbsRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
}
