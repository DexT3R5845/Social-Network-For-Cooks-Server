package com.edu.netc.bakensweets.service;

import com.edu.netc.bakensweets.model.account.Account;
import com.edu.netc.bakensweets.model.account.dto.AccountSignUpDto;
import com.edu.netc.bakensweets.model.credentials.Credentials;

public interface AccountService {

    Account getAccountByCred(Credentials credentialsDto);

    Account register(AccountSignUpDto account);
}
