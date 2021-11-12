package com.edu.netc.bakensweets.service;

import com.edu.netc.bakensweets.model.Account;

public interface AccountService {
    String signin(String email, String password);
    String signUp();
    Account getByEmail(String email);
}
