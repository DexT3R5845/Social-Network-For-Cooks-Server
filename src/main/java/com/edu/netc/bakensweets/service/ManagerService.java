package com.edu.netc.bakensweets.service;

import com.edu.netc.bakensweets.dto.AccountDTO;
import com.edu.netc.bakensweets.dto.AccountPersonalInfoDTO;

import java.util.Map;

public interface ManagerService {
    String createModerator(AccountDTO accountDTO);
    Map<String, Object> getAllModerators(String search, int pageNum, int size);
    AccountPersonalInfoDTO findById(long id);
    AccountPersonalInfoDTO updateModerator(long id, AccountPersonalInfoDTO account);
}
