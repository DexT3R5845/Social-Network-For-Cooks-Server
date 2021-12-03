package com.edu.netc.bakensweets.dto;

import com.edu.netc.bakensweets.model.Account;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

@Data
@AllArgsConstructor
public class AccountsPerPageDTO {
    private Collection<AccountPersonalInfoDTO> accounts;
    private int currentPage;
    private int pageCount;

}