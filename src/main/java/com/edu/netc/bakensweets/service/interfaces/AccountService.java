package com.edu.netc.bakensweets.service.interfaces;

import com.edu.netc.bakensweets.dto.account.UpdateAccountDTO;
import com.edu.netc.bakensweets.dto.account.AccountDTO;
import com.edu.netc.bakensweets.dto.account.AccountPersonalInfoDTO;
import com.edu.netc.bakensweets.dto.account.AccountsPerPageDTO;
import com.edu.netc.bakensweets.model.Account;

public interface AccountService {
    UpdateAccountDTO updateProfile(UpdateAccountDTO accountDTO, String email);
    String changePassword(String oldPassword, String newPassword, String email);
    String signIn(String email, String password);
    String signUp (AccountDTO accountDTO);
    Account getByEmail(String email);
    AccountsPerPageDTO getAllBySearchAccounts(String search, int currentPage, int limit);
    AccountPersonalInfoDTO findById (long id);
    AccountsPerPageDTO getAllBySearchModerators(String search, int currentPage, int limit);
    void updatePersonalInfo(AccountPersonalInfoDTO accountDto);
    void updateModerStatus(long id, boolean status);
}
