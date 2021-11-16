package com.edu.netc.bakensweets.service;

import com.edu.netc.bakensweets.dto.AccountDTO;
import com.edu.netc.bakensweets.model.Account;

public interface AccountService {
    String signIn(String email, String password);
    String signUp(AccountDTO accountDTO);
    String updateProfile(AccountDTO accountDTO,String token);
    String changePassword(String oldPassword,String newPassword,String token);
    Account getByEmail(String email);
}
