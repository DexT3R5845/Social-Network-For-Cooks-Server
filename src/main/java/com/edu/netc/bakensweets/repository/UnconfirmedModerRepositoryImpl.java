package com.edu.netc.bakensweets.repository;

import com.edu.netc.bakensweets.model.Account;
import com.edu.netc.bakensweets.model.UnconfirmedModerator;
import com.edu.netc.bakensweets.repository.interfaces.UnconfirmedModerRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UnconfirmedModerRepositoryImpl extends BaseJdbsRepository implements UnconfirmedModerRepository {

    @Value("${sql.unconfirmedModer.create}")
    private String sqlCreateQuery;

    @Value("${sql.unconfirmedModer.getByToken}")
    private String sqlGetByToken;

    @Value("${sql.unconfirmedModer.sqlSumOfUsagesEmail}")
    private String sqlSumOfUsagesEmail;

    public UnconfirmedModerRepositoryImpl (JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void create (UnconfirmedModerator moderator) {
        jdbcTemplate.update(
                sqlCreateQuery, moderator.getModerToken(), moderator.getExpireDate(), moderator.getEmail(),
                moderator.getFirstName(), moderator.getLastName(), moderator.getBirthDate(), moderator.getGender().name()
        );
    }

    @Override
    public UnconfirmedModerator getByToken (String token) {
        return jdbcTemplate.queryForObject(sqlGetByToken,  new BeanPropertyRowMapper<>(UnconfirmedModerator.class), token);

    }

    @Override
    public Integer findUsagesOfEmail(String email) {
        return jdbcTemplate.queryForObject(sqlSumOfUsagesEmail, Integer.class, email, email);
    }

    @Override
    public void update (UnconfirmedModerator moderator) {

    }

    @Override
    public void deleteById (Integer integer) {

    }

    @Override
    public UnconfirmedModerator findById (Integer id) {
        return null;
    }
}
