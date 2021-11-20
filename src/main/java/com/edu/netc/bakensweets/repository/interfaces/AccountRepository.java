package com.edu.netc.bakensweets.repository.interfaces;

import com.edu.netc.bakensweets.model.Account;
import com.edu.netc.bakensweets.model.AccountRole;

import java.util.Collection;

public interface AccountRepository extends BaseCrudRepository<Account, Long>{
    Account findByEmail(String email);
    int getAllSearchedCount(String search, AccountRole role);
    Collection<Account> getAllSearchedWithLimit(String search, int limit, int offset, AccountRole role);
    void updateStatus(long id, AccountRole role);
}
