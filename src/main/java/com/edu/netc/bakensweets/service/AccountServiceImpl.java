package com.edu.netc.bakensweets.service;

import com.edu.netc.bakensweets.model.account.Account;
import com.edu.netc.bakensweets.model.account.dto.AccountSignUpDto;
import com.edu.netc.bakensweets.model.credentials.Credentials;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Override
    public Account getAccountByCred (Credentials credentialsDto) {
        //TODO auth logic & validate, ...
        return null;
    }

    @Override
    public Account register (AccountSignUpDto account) {
        //TODO validate, ...
        return null;
    }
}
