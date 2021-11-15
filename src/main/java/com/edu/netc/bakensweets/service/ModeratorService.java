package com.edu.netc.bakensweets.service;

import com.edu.netc.bakensweets.dto.AccountDTO;
import com.edu.netc.bakensweets.dto.AccountDemoDTO;
import com.edu.netc.bakensweets.model.Account;

import java.util.Map;

public interface ModeratorService  {
    String createModerator(AccountDTO accountDTO);
    public Map<String, Object> getAllModerators(String search, int pageNum);
    public AccountDemoDTO findById(long id);
    public AccountDemoDTO updateModerator(long id, AccountDemoDTO account);
}
