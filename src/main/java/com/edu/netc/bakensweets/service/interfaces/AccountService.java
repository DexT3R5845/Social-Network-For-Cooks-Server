package com.edu.netc.bakensweets.service.interfaces;

import com.edu.netc.bakensweets.dto.UpdateAccountDTO;
import com.edu.netc.bakensweets.dto.AccountDTO;
import com.edu.netc.bakensweets.dto.AccountPersonalInfoDTO;
import com.edu.netc.bakensweets.dto.AccountsPerPageDTO;
import com.edu.netc.bakensweets.model.Account;

public interface AccountService {
    void updateProfile(UpdateAccountDTO accountDTO, String email);
    void changePassword(String oldPassword, String newPassword, String email);
    String signIn(String username, String password, String recaptcha_token, String ip);
    void signUp (AccountDTO accountDTO);

    AccountsPerPageDTO getAllBySearchAccounts(String search, int currentPage, int limit, boolean order, String gender);
    UpdateAccountDTO getUserInfoByEmail(String email);
    AccountPersonalInfoDTO findById (long id);
    AccountsPerPageDTO getAllBySearchModerators(String search, int currentPage, int limit, boolean order, String gender, String status);
    void updatePersonalInfo(AccountPersonalInfoDTO accountDto);
    void updateModerStatus(long id, boolean status);
}
