package com.edu.netc.bakensweets.repository;

import com.edu.netc.bakensweets.model.PasswordResetToken;
import com.edu.netc.bakensweets.repository.interfaces.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PasswordResetTokenRepositoryImpl extends BaseJdbsRepository implements PasswordResetTokenRepository {
    @Value("${sql.passwordResetToken.create}")
    private String sqlQueryCreate;
    @Value("${sql.passwordResetToken.findByAccountId}")
    private String sqlQueryFindByAccountId;
    @Value("${sql.disableAllActiveSessionResetLink}")
    private String sqlQueryDisableAllTokens;
    public PasswordResetTokenRepositoryImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void create(PasswordResetToken passResetToken) {
        jdbcTemplate.update(sqlQueryCreate, passResetToken.getResetToken(),
                passResetToken.getExpiryDate(),
                passResetToken.getAccountId(),
                passResetToken.isActive());
    }

    @Override
    public void update(PasswordResetToken item) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public PasswordResetToken findById(Long aLong) {
        return null;
    }

    @Override
    public PasswordResetToken findByAccountId(Long accountId) {
        return jdbcTemplate.queryForObject(sqlQueryFindByAccountId, new BeanPropertyRowMapper<>(PasswordResetToken.class), accountId);
    }

    @Override
    public void disableAllTokensByAccountId(Long id) {
        jdbcTemplate.update(sqlQueryDisableAllTokens, id);
    }
}
