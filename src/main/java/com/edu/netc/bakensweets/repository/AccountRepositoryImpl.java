package com.edu.netc.bakensweets.repository;

import com.edu.netc.bakensweets.model.Account;
import com.edu.netc.bakensweets.repository.interfaces.AccountRepository;
import com.edu.netc.bakensweets.repository.rawMappers.AccountRawMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AccountRepositoryImpl extends BaseJdbsRepository implements AccountRepository {
    private String sqlQueryCreate;
    private String sqlQueryUpdate;
    private String sqlQueryDelete;
    @Value("${sql.user.getById}")
    private String sqlQueryGetById;
    @Value("${sql.user.findByEmail}")
    private String sqlQueryFindByEmail;
    @Value("${sql.user.findByEmailAndPassword}")
    private String sqlQueryFindByEmailAndPassword;
    @Value("${sql.user.count}")
    private String sqlQueryCount;

    public AccountRepositoryImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void create(Account account) {
        jdbcTemplate.update(sqlQueryCreate, account.getId(), account.getFirstName(), account.getLastName(),
                account.getBirthDate(), account.getGender(), account.getImgUrl(), account.getAccountRole());
    }

    @Override
    public void update(Account account) {
        jdbcTemplate.update(sqlQueryCreate, account.getId(), account.getFirstName(), account.getLastName(),
                account.getBirthDate(), account.getGender(), account.getImgUrl(), account.getAccountRole());
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update(sqlQueryDelete, id);
    }

    @Override
    public Account getById(Long id) {
        return jdbcTemplate.queryForObject(sqlQueryGetById, new AccountRawMapper(), id);
    }

    public Account findByEmail(String email){
        try {
            Account acc = jdbcTemplate.queryForObject(sqlQueryFindByEmail, new AccountRawMapper(), email);
            return acc;
        }
        catch (Exception ex){System.out.println(ex.getMessage());}
        return null;
    }

    public Account findByEmailAndPassword(String email, String password){
            return jdbcTemplate.queryForObject(sqlQueryFindByEmailAndPassword, new AccountRawMapper(), email, password);
    }

    public int count(){
        return jdbcTemplate.queryForObject(sqlQueryCount, Integer.class);
    }
}
