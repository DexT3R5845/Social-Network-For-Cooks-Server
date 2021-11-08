package com.edu.netc.bakensweets.dao;

import com.edu.netc.bakensweets.model.account.Account;

import java.sql.SQLException;
import java.util.HashSet;

public class AccountDao implements Dao<Account, Integer> {
    @Override
    public boolean create (Account entity) throws SQLException {
        return false;
    }

    @Override
    public HashSet<Account> getAll () {
        return null;
    }

    @Override
    public Account getByID (Integer integer) {
        return null;
    }

    @Override
    public boolean update (Account entity) {
        return false;
    }

    @Override
    public boolean delete (Integer integer) {
        return false;
    }
}
