package com.edu.netc.bakensweets.service;

import com.edu.netc.bakensweets.dto.AccountDTO;
import com.edu.netc.bakensweets.dto.UpdateAccountDTO;
import com.edu.netc.bakensweets.exception.CustomException;
import com.edu.netc.bakensweets.mapperConfig.AccountMapper;
import com.edu.netc.bakensweets.mapperConfig.CredentialsMapper;
import com.edu.netc.bakensweets.mapperConfig.UpdateAccountMapper;
import com.edu.netc.bakensweets.model.Account;
import com.edu.netc.bakensweets.model.AccountRole;
import com.edu.netc.bakensweets.model.Credentials;
import com.edu.netc.bakensweets.repository.AccountRepositoryImpl;
import com.edu.netc.bakensweets.repository.interfaces.AccountRepository;
import com.edu.netc.bakensweets.repository.interfaces.CredentialsRepository;
import com.edu.netc.bakensweets.security.JwtTokenProvider;
import com.edu.netc.bakensweets.utils.Utils;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final CredentialsRepository credentialsRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final CredentialsMapper credentialsMapper;
    private final AccountMapper accountMapper;
    private final UpdateAccountMapper updateAccountMapper;

    public AccountServiceImpl(AccountRepository accountRepository, CredentialsRepository credentialsRepository, JwtTokenProvider jwtTokenProvider,
                              AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, CredentialsMapper credentialsMapper,
                              AccountMapper accountMapper, UpdateAccountMapper updateAccountMapper) {
        this.accountRepository = accountRepository;
        this.credentialsRepository = credentialsRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.credentialsMapper = credentialsMapper;
        this.accountMapper = accountMapper;
        this.updateAccountMapper = updateAccountMapper;
    }

    @Override
    public String signIn(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return jwtTokenProvider.createToken(username, accountRepository.findByEmail(username).getAccountRole());
        } catch (AuthenticationException e) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "Invalid username/password supplied");
        }
    }

    @Override
    public String signUp(AccountDTO accountDTO) {
        createNewAccount(accountDTO, AccountRole.ROLE_USER);
        return "Reg Success";
    }

    private void createNewAccount(AccountDTO accountDTO, AccountRole accountRole){
        long uniqueId = Utils.generateUniqueId();
        Credentials credentials = credentialsMapper.accountDTOtoCredentials(accountDTO);
        Account account = accountMapper.accountDTOtoAccounts(accountDTO);
        credentials.setId(uniqueId);
        account.setId(uniqueId);
        account.setAccountRole(accountRole);
        credentials.setPassword(passwordEncoder.encode(credentials.getPassword()));

        credentialsRepository.create(credentials);
        accountRepository.create(account);
    }

    @Override
    public UpdateAccountDTO updateProfile(UpdateAccountDTO accountDTO, String username) {
        Credentials credentials = credentialsRepository.findByEmail(username);
        Account accountUpdate = updateAccountMapper.updateAccountDTOtoAccounts(accountDTO);
        accountUpdate.setId(credentials.getId());
        accountRepository.update(accountUpdate);
        return accountDTO;
    }

    @Override
    public String changePassword(String oldPassword, String newPassword, String username) {
        Credentials credentials = credentialsRepository.findByEmail(username);
        String requiredPassword = credentials.getPassword();
        if (!passwordEncoder.matches(oldPassword, requiredPassword)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Entered invalid old password");
        }
        credentials.setPassword(passwordEncoder.encode(newPassword));
        credentialsRepository.update(credentials);
        return "Password successfully changed";
    }

    @Override
    public Account getByEmail(String email) {
        return accountRepository.findByEmail(email);
    }


}
