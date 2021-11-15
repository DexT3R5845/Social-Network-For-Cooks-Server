package com.edu.netc.bakensweets.repository;

import com.edu.netc.bakensweets.model.Account;
import com.edu.netc.bakensweets.model.AccountRole;
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

    @Value("${sql.moderator.countFindAll}")
    private String sqlCountFindAll;

    @Value("${sql.moderator.findAll}")
    private String sqlFindAllWithLimit;

    @Value("${sql.account.updateAccData}")
    private String sqlUpdateAccData;

    @Value("${page.showAll.limit}")
    private int pageLimit;

    @Override   //TODO CHANGE ROLE TO MODERATOR, IT`S DEMO VARIANT
    public int getAllModersCount (String search) {
        Integer count = jdbcTemplate.queryForObject(
                sqlCountFindAll, Integer.class, AccountRole.ROLE_USER.getAuthority(), search
        );
        return count == null ? 0 : count;
    }

    @Override   //TODO CHANGE ROLE TO MODERATOR, IT`S DEMO VARIANT
    public List<Account> getAllModers (String search, int rowOffset) {
        return jdbcTemplate.query(
                sqlFindAllWithLimit,
                new BeanPropertyRowMapper<>(Account.class),
                AccountRole.ROLE_USER.getAuthority(), search, pageLimit, rowOffset
        );
    }

    @Override
    public void create (Account item) {

    }

    @Override
    public void update (Account account) {
        jdbcTemplate.update(sqlUpdateAccData, account.getFirstName(), account.getLastName(),
                account.getBirthDate(), account.getGender().name(), account.getImgUrl(), account.getId());
    }

    @Override
    public void deleteById (Long aLong) {

    }

    @Override
    public Account findById (Long id) {
        return null;
    }
}
