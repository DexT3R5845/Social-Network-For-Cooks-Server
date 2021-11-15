package com.edu.netc.bakensweets.service;

import com.edu.netc.bakensweets.dto.AccountDTO;
import com.edu.netc.bakensweets.dto.AccountDemoDTO;
import com.edu.netc.bakensweets.mapperConfig.AccountMapper;
import com.edu.netc.bakensweets.mapperConfig.CredentialsMapper;
import com.edu.netc.bakensweets.model.Account;
import com.edu.netc.bakensweets.model.AccountRole;
import com.edu.netc.bakensweets.model.Credentials;
import com.edu.netc.bakensweets.repository.interfaces.AccountRepository;
import com.edu.netc.bakensweets.repository.interfaces.CredentialsRepository;
import com.edu.netc.bakensweets.repository.interfaces.ModeratorRepository;
import com.edu.netc.bakensweets.utils.UniqueGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ModeratorServiceImpl implements ModeratorService {
    private final AccountRepository accountRepository;
    private final CredentialsRepository credentialsRepository;
    private final ModeratorRepository moderatorRepository;
    private final PasswordEncoder passwordEncoder;
    private final CredentialsMapper credentialsMapper;
    private final AccountMapper accountMapper;

    @Value("${page.showAll.limit}")
    private int pageLimit;

    public ModeratorServiceImpl (AccountRepository accountRepository, CredentialsRepository credentialsRepository, ModeratorRepository moderatorRepository,
                                 PasswordEncoder passwordEncoder, CredentialsMapper credentialsMapper, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.credentialsRepository = credentialsRepository;
        this.moderatorRepository = moderatorRepository;
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
    public Map<String, Object> getAllModerators(String search, int currentPage) {
        Map<String, Object> response = new HashMap<>();

        int accCount = moderatorRepository.getAllModersCount(search);
        int pageCount = accCount % pageLimit == 0 ? accCount / pageLimit : accCount / pageLimit + 1;
        List<Account> moderators = moderatorRepository.getAllModers(search, (currentPage - 1) * pageLimit);

        response.put("moderators", moderators);
        response.put("currentPage", currentPage);
        response.put("pageCount", pageCount);

        return response;
    }

    @Override
    public AccountDemoDTO findById (long id) {
        Account moderator = accountRepository.findById(id);
        Credentials credentials = credentialsRepository.findById(id);
        AccountDemoDTO account = AccountDemoDTO.accountToDTO(moderator);
        account.setEmail(credentials.getEmail());
        return account;
    }

    @Override
    public AccountDemoDTO updateModerator(long id, AccountDemoDTO accountDto) {
        Account account = accountMapper.accountDemoDTOtoAccounts(accountDto);
        account.setId(id);
        moderatorRepository.update(account);
        return accountDto;
    }
}