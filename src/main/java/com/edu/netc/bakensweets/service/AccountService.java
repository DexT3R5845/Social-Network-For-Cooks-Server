package com.edu.netc.bakensweets.service;

import com.edu.netc.bakensweets.dto.AccountDTO;
import com.edu.netc.bakensweets.dto.AccountPersonalInfoDTO;
import com.edu.netc.bakensweets.dto.AccountsPerPageDTO;
import com.edu.netc.bakensweets.model.Account;

import java.util.Map;

public interface AccountService {
    String signIn(String email, String password);
    String createAccount(AccountDTO accountDTO);
    Account getByEmail(String email);

    String createModerator(AccountDTO accountDTO);
    AccountsPerPageDTO getAllBySearchAccounts(String search, int currentPage, int limit);
    AccountsPerPageDTO getAllBySearchModerators(String search, int currentPage, int limit);
    AccountPersonalInfoDTO findById(long id);
    void updatePersonalInfo(AccountPersonalInfoDTO account);
}
