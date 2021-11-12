package com.edu.netc.bakensweets.repository.rawMappers;

import com.edu.netc.bakensweets.model.Account;
import com.edu.netc.bakensweets.model.AccountRole;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRawMapper implements RowMapper<Account> {
    @Override
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
        Account account = new Account();
        account.setEmail(rs.getString("email"));
        account.setPassword(rs.getString("password"));
        account.setId(rs.getLong("account_id"));
        account.setFirstName(rs.getString("first_name"));

        account.setLastName(rs.getString("last_name"));
        account.setBirthDate(rs.getDate("birth_date"));
        account.setGender(rs.getString("gender"));
        account.setImgUrl(rs.getString("user_img"));
        account.setAccountRole(AccountRole.valueOf(rs.getString("account_role_name")));
        return account;
    }
}
