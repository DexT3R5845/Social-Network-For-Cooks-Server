package com.edu.netc.bakensweets.service;

import com.edu.netc.bakensweets.dto.AccountDTO;
import com.edu.netc.bakensweets.dto.AccountPersonalInfoDTO;
import com.edu.netc.bakensweets.mapperConfig.AccountMapper;
import com.edu.netc.bakensweets.mapperConfig.CredentialsMapper;
import com.edu.netc.bakensweets.model.Account;
import com.edu.netc.bakensweets.model.AccountRole;
import com.edu.netc.bakensweets.model.Credentials;
import com.edu.netc.bakensweets.repository.interfaces.AccountRepository;
import com.edu.netc.bakensweets.repository.interfaces.CredentialsRepository;
import com.edu.netc.bakensweets.utils.UniqueGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class ManagerServiceImpl implements ManagerService {
    private final AccountRepository accountRepository;
    private final CredentialsRepository credentialsRepository;
    private final PasswordEncoder passwordEncoder;
    private final CredentialsMapper credentialsMapper;
    private final AccountMapper accountMapper;

    public ManagerServiceImpl (AccountRepository accountRepository, CredentialsRepository credentialsRepository,
                               PasswordEncoder passwordEncoder, CredentialsMapper credentialsMapper, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.credentialsRepository = credentialsRepository;
        this.passwordEncoder = passwordEncoder;
        this.credentialsMapper = credentialsMapper;
        this.accountMapper = accountMapper;
    }

    @Override   // хороший ли тон ссылаться из одного сервиса в другой во избежание дубля?
    public String createModerator (AccountDTO accountDTO) {
        long uniqueId = UniqueGenerator.generateUniqueId();

        Credentials credentials = credentialsMapper.accountDTOtoCredentials(accountDTO);
        Account account = accountMapper.accountDTOtoAccounts(accountDTO);

        credentials.setId(uniqueId);
        account.setId(uniqueId);

        account.setAccountRole(AccountRole.ROLE_MODERATOR);
        credentials.setPassword(passwordEncoder.encode(credentials.getPassword()));

        credentialsRepository.create(credentials);
        accountRepository.create(account);

        return "moder successfully added";
    }

    @Override
    public Map<String, Object> getAllModerators(String search, int currentPage, int limit) {
        Map<String, Object> response = new HashMap<>();

        int accCount = accountRepository.getAllSearchedCount(search);
        int pageCount = accCount % limit == 0 ? accCount / limit : accCount / limit + 1;

        Collection<Account> moderators =
                accountRepository.getAllSearchedWithLimit(search, (currentPage - 1) * limit, limit);

        response.put("moderators", moderators);
        response.put("currentPage", currentPage);
        response.put("pageCount", pageCount);

        return response;
    }

    @Override
    public AccountPersonalInfoDTO findById (long id) {
        Account moderator = accountRepository.findById(id);
        Credentials credentials = credentialsRepository.findById(id);
        AccountPersonalInfoDTO account = accountMapper.accountToAccountPersonalInfoDto(moderator);
        account.setEmail(credentials.getEmail());
        return account;
    }

    @Override
    public AccountPersonalInfoDTO updateModerator(long id, AccountPersonalInfoDTO accountDto) {
        Account account = accountMapper.accountPersonalInfoDTOtoAccounts(accountDto);
        accountRepository.update(account);
        return accountDto;
    }
}