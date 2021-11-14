package com.edu.netc.bakensweets.service;

import com.edu.netc.bakensweets.dto.AccountDTO;

import java.util.Map;

public interface ModeratorService  {
    String createModerator(AccountDTO accountDTO);
    public Map<String, Object> getAllModerators(String search, int pageNum);
}
