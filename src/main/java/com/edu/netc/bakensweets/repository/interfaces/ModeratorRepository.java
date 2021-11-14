package com.edu.netc.bakensweets.repository.interfaces;

import com.edu.netc.bakensweets.model.Account;

import java.util.List;

public interface ModeratorRepository extends BaseCrudRepository<Account, Long> {
    public int getAllModersCount(String search);
    public List<Account> getAllModers(String search, int pageNum);
}
