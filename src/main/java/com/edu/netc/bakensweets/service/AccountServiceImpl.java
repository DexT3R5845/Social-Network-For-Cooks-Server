package com.edu.netc.bakensweets.service;

import com.edu.netc.bakensweets.dto.AccountDTO;
import com.edu.netc.bakensweets.mapperConfig.AccountMapper;
import com.edu.netc.bakensweets.mapperConfig.CredentialsMapper;
import com.edu.netc.bakensweets.model.Account;
import com.edu.netc.bakensweets.model.AccountRole;
import com.edu.netc.bakensweets.model.Credentials;
import com.edu.netc.bakensweets.repository.interfaces.AccountRepository;
import com.edu.netc.bakensweets.repository.interfaces.CredentialsRepository;
import com.edu.netc.bakensweets.security.JwtTokenProvider;
import com.edu.netc.bakensweets.utils.UniqueGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
private final AccountRepository accountRepository;
private final CredentialsRepository credentialsRepository;
private final JwtTokenProvider jwtTokenProvider;
private final AuthenticationManager authenticationManager;
private final PasswordEncoder passwordEncoder;
private final CredentialsMapper credentialsMapper;
private final AccountMapper accountMapper;

public AccountServiceImpl(AccountRepository accountRepository, CredentialsRepository credentialsRepository,JwtTokenProvider jwtTokenProvider,
                          AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, CredentialsMapper credentialsMapper,
                          AccountMapper accountMapper){
    this.accountRepository = accountRepository;
    this.credentialsRepository = credentialsRepository;
    this.jwtTokenProvider = jwtTokenProvider;
    this.authenticationManager = authenticationManager;
    this.passwordEncoder = passwordEncoder;
    this.credentialsMapper = credentialsMapper;
    this.accountMapper = accountMapper;
}
    @Override
    public String signIn(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return jwtTokenProvider.createToken(username, accountRepository.findByEmail(username).getAccountRole());
        } catch (AuthenticationException e) {
            throw new com.edu.netc.bakensweets.exception.CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    public String signUp(AccountDTO accountDTO) {
    createNewAccount(accountDTO, AccountRole.ROLE_USER);
    return "Reg Success";
    }

    private void createNewAccount(AccountDTO accountDTO, AccountRole accountRole){
        long uniqueId = UniqueGenerator.generateUniqueId();

        Credentials credentials = credentialsMapper.accountDTOtoCredentials(accountDTO);
        Account account = accountMapper.accountDTOtoAccounts(accountDTO);

        credentials.setId(uniqueId);
        account.setId(uniqueId);

        account.setAccountRole(accountRole);
        credentials.setPassword(passwordEncoder.encode(credentials.getPassword()));

        credentialsRepository.create(credentials);
        accountRepository.create(account);
    }

    public Account getByEmail(String email) {
        return accountRepository.findByEmail(email);
    }
}
