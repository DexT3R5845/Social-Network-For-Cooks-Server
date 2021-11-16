package com.edu.netc.bakensweets.service;

import com.edu.netc.bakensweets.dto.AccountDTO;
import com.edu.netc.bakensweets.model.Account;

public interface AccountService {
    String signIn(String email, String password);
    String signUp(AccountDTO accountDTO);
    Account getByEmail(String email);
}
