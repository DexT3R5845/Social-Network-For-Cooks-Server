package com.edu.netc.bakensweets.repository.interfaces;

import com.edu.netc.bakensweets.model.Account;

public interface AccountRepository extends BaseCrudRepository<Account, Long>{
    Account findByEmail(String email);
}
