package com.edu.netc.bakensweets.repository;

import com.edu.netc.bakensweets.model.Account;
import com.edu.netc.bakensweets.model.AccountRole;
import com.edu.netc.bakensweets.repository.interfaces.AccountRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class AccountRepositoryImpl extends BaseJdbsRepository implements AccountRepository {

    @Value("${sql.account.create}")
    private String sqlQueryCreate;
    @Value("${sql.account.findById}")
    private String sqlQueryGetById;
    @Value("${sql.account.findByEmail}")
    private String sqlQueryFindByEmail;

    @Value("${sql.account.countAllBySearch}")
    private String sqlCountFindAll;

    @Value("${sql.account.findAllBySearch}")
    private String sqlFindAllBySearch;

    @Value("${sql.account.updateAccData}")
    private String sqlUpdateAccData;

    public AccountRepositoryImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void create(Account account) {
        jdbcTemplate.update(sqlQueryCreate, account.getId(), account.getFirstName(), account.getLastName(),
                account.getBirthDate(), account.getGender().name(), account.getAccountRole().getAuthority());
    }

    @Override
    public void update(Account account) {
        jdbcTemplate.update(sqlUpdateAccData, account.getFirstName(), account.getLastName(),
                account.getBirthDate(), account.getImgUrl(), account.getGender().name(), account.getId());
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Account findById(Long id) {
        return jdbcTemplate.queryForObject(sqlQueryGetById, new BeanPropertyRowMapper<>(Account.class), id);
    }

    @Override
    public Account findByEmail(String email) {
        return jdbcTemplate.queryForObject(sqlQueryFindByEmail, new BeanPropertyRowMapper<>(Account.class), email);
    }

    @Override
    public int getAllSearchedCount (String search, AccountRole role) {
        Integer count = jdbcTemplate.queryForObject(
                sqlCountFindAll, Integer.class, role.getAuthority(), search, search
        );
        return count == null ? 0 : count;
    }

    @Override
    public Collection<Account> getAllSearchedWithLimit (String search, int limit, int offset, AccountRole role) {
        return jdbcTemplate.query(
                sqlFindAllBySearch,
                new BeanPropertyRowMapper<>(Account.class),
                role.getAuthority(), search, search, limit, offset
        );
    }
}
