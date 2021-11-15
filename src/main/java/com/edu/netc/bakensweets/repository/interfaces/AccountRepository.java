package com.edu.netc.bakensweets.repository.interfaces;

import com.edu.netc.bakensweets.model.Account;

import java.util.Collection;
import java.util.List;

public interface AccountRepository extends BaseCrudRepository<Account, Long>{
    Account findByEmail(String email);
    public int getAllSearchedCount(String search);
    public Collection<Account> getAllSearchedWithLimit(String search, int pageNum, int limit);
}
