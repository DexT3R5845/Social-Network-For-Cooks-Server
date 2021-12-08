package com.edu.netc.bakensweets.repository;

import com.edu.netc.bakensweets.model.Account;
import com.edu.netc.bakensweets.model.AccountRole;
import com.edu.netc.bakensweets.repository.interfaces.AccountRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Array;
import java.util.Collection;


@Repository
public class AccountRepositoryImpl extends BaseJdbcRepository implements AccountRepository {

    @Value("${sql.account.create}")
    private String sqlQueryCreate;

    @Value("${sql.account.findById}")
    private String sqlQueryGetById;

    @Value("${sql.account.findByEmail}")
    private String sqlQueryFindByEmail;

    @Value("${sql.account.countAllBySearch}")
    private String sqlCountFindAll;

    @Value("${sql.account.findAllBySearchDesc}")
    private String sqlFindAllBySearchDesc;

    @Value("${sql.account.findAllBySearchAsc}")
    private String sqlFindAllBySearchAsc;

    @Value("${sql.account.updateAccData}")
    private String sqlUpdateAccData;

    @Value("${sql.account.updateModerStatus}")
    private String sqlUpdateModerStatus;

    public AccountRepositoryImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void create(Account account) {
        jdbcTemplate.update(sqlQueryCreate, account.getId(), account.getFirstName(), account.getLastName(),
                account.getBirthDate(), account.getGender().name(), account.getAccountRole().getAuthority());
    }

    @Override
    public boolean update(Account account) {
        jdbcTemplate.update(sqlUpdateAccData, account.getFirstName(), account.getLastName(),
                account.getBirthDate(), account.getImgUrl(), account.getGender().name(), account.getId());
        return true;
    }

    @Override
    public void updateStatus(long id, boolean status) {
        jdbcTemplate.update(sqlUpdateModerStatus, status, id);
    }

    @Override
    public boolean deleteById(Long id) {
        throw new UnsupportedOperationException();
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
    public int countAccountsBySearch (String search, AccountRole role, String gender, String status) {
        Integer count = jdbcTemplate.queryForObject(
                sqlCountFindAll, Integer.class, role.getAuthority(), search, search, gender, status
        );
        return count == null ? 0 : count;
    }

    @Override
    public Collection<Account> findAccountsBySearch (String search, String gender, AccountRole role,
                                                     String status, int limit, int offset, boolean order) {

        String searchQuery = order ? sqlFindAllBySearchAsc : sqlFindAllBySearchDesc;
        return jdbcTemplate.query(
                searchQuery, new BeanPropertyRowMapper<>(Account.class),
                role.getAuthority(), search, search, gender, status, limit, offset
        );
    }
}
