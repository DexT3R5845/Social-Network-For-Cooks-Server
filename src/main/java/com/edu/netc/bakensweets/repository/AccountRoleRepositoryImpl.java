package com.edu.netc.bakensweets.repository;

/*import com.bakensweets.model.AccountRole;
import com.bakensweets.repository.interfaces.AccountRoleRepository;
import com.bakensweets.repository.rawMappers.AccountRoleRawMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class AccountRoleRepositoryImpl implements AccountRoleRepository {
private final JdbcTemplate jdbcTemplate;
private String sqlQueryCreate;
private String sqlQueryUpdate;
private String sqlQueryDelete;
private String sqlQueryGetById;

public AccountRoleRepositoryImpl(JdbcTemplate jdbcTemplate){
    this.jdbcTemplate = jdbcTemplate;
}
    @Override
    public void create(AccountRole item) {
    jdbcTemplate.update(sqlQueryCreate, item.getAuthority());
    }

    @Override
    public void update(AccountRole item) {
        jdbcTemplate.update(sqlQueryUpdate, item.getName());
    }

    @Override
    public void deleteById(String id) {
    jdbcTemplate.update(sqlQueryDelete, id);
    }

    public AccountRole getById(String id){
    return jdbcTemplate.queryForObject(sqlQueryGetById, new AccountRoleRawMapper(), id);
    }
}*/
