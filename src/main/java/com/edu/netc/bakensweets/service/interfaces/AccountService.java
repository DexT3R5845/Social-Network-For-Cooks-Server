package com.edu.netc.bakensweets.service.interfaces;

import com.edu.netc.bakensweets.dto.*;

public interface AccountService {
    void updateProfile(UpdateAccountDTO accountDTO, String email);
    void changePassword(String oldPassword, String newPassword, String email);
    String signIn(String username, String password, String recaptcha_token, String ip);
    void signUp (AccountDTO accountDTO);

    PaginationDTO getAllBySearchAccounts(String search, int currentPage, int limit, boolean order, String gender);
    UpdateAccountDTO getUserInfoByEmail(String email);
    AccountPersonalInfoDTO findById (long id);
    PaginationDTO getAllBySearchModerators(String search, int currentPage, int limit, boolean order, String gender, String status);
    void updatePersonalInfo(AccountPersonalInfoDTO accountDto);
    void updateModerStatus(long id);
}
