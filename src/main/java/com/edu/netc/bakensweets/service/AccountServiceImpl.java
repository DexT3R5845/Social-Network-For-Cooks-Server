package com.edu.netc.bakensweets.service;

import com.edu.netc.bakensweets.dto.AccountDTO;
import com.edu.netc.bakensweets.dto.AccountPersonalInfoDTO;
import com.edu.netc.bakensweets.dto.AccountsPerPageDTO;
import com.edu.netc.bakensweets.dto.UpdateAccountDTO;
import com.edu.netc.bakensweets.exception.CustomException;
import com.edu.netc.bakensweets.mapperConfig.AccountMapper;
import com.edu.netc.bakensweets.mapperConfig.CredentialsMapper;
import com.edu.netc.bakensweets.model.Account;
import com.edu.netc.bakensweets.model.AccountRole;
import com.edu.netc.bakensweets.model.Credentials;
import com.edu.netc.bakensweets.repository.interfaces.AccountRepository;
import com.edu.netc.bakensweets.repository.interfaces.CredentialsRepository;
import com.edu.netc.bakensweets.security.JwtTokenProvider;
import com.edu.netc.bakensweets.service.interfaces.AccountService;
import com.edu.netc.bakensweets.utils.Utils;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;


@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final CredentialsRepository credentialsRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final CredentialsMapper credentialsMapper;
    private final AccountMapper accountMapper;

    public AccountServiceImpl(AccountRepository accountRepository, CredentialsRepository credentialsRepository, JwtTokenProvider jwtTokenProvider,
                              AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, CredentialsMapper credentialsMapper,
                              AccountMapper accountMapper) {
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
            throw new CustomException(HttpStatus.UNAUTHORIZED, "Invalid username/password supplied");
        }
    }

    @Override
    public String signUp (AccountDTO accountDTO) {
        createNewAccount(accountDTO, AccountRole.ROLE_USER);
    return "Reg Success";
    }

    private void createNewAccount (AccountDTO accountDTO, AccountRole accountRole){
        long uniqueId = Utils.generateUniqueId();

        Credentials credentials = credentialsMapper.accountDTOtoCredentials(accountDTO);
        Account account = accountMapper.accountDTOtoAccounts(accountDTO);

        credentials.setId(uniqueId);
        credentials.setPassword(passwordEncoder.encode(credentials.getPassword()));
        account.setId(uniqueId);
        account.setAccountRole(accountRole);

        credentialsRepository.create(credentials);
        accountRepository.create(account);
    }

    @Override
    public UpdateAccountDTO updateProfile(UpdateAccountDTO accountDTO, String email) {
        Credentials credentials = credentialsRepository.findByEmail(email);
        Account accountUpdate = accountMapper.updateAccountDTOtoAccount(accountDTO);
        accountUpdate.setId(credentials.getId());
        accountRepository.update(accountUpdate);
        return accountDTO;
    }

    @Override
    public String changePassword(String oldPassword, String newPassword, String email) {
        Credentials credentials = credentialsRepository.findByEmail(email);
        String requiredPassword = credentials.getPassword();
        if (!passwordEncoder.matches(oldPassword, requiredPassword)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Entered invalid old password");
        }
        credentials.setPassword(passwordEncoder.encode(newPassword));
        credentialsRepository.update(credentials);
        return "Password successfully changed";
    }

    @Override
    public void updatePersonalInfo(AccountPersonalInfoDTO accountDto) {
        Account account = accountMapper.accountPersonalInfoDTOtoAccounts(accountDto);
        accountRepository.update(account);
    }

    @Override
    public void updateModerStatus(long id, boolean status) {
        try {
            if (status) accountRepository.updateStatus(id, AccountRole.ROLE_BAN);
            else accountRepository.updateStatus(id, AccountRole.ROLE_MODERATOR);
        } catch (DataAccessException ex) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "There is no account with such id");
        }
    }

    @Override
    public Account getByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public AccountsPerPageDTO getAllBySearchAccounts(String search, int currentPage, int limit) {
        return getAllByRole(search, currentPage, limit, AccountRole.ROLE_USER);
    }

    @Override
    public AccountsPerPageDTO getAllBySearchModerators(String search, int currentPage, int limit) {
        try {
            return getAllByRole(search, currentPage, limit, AccountRole.ROLE_MODERATOR);
        } catch (DataAccessException ex) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "db/repository error");
        }
    }

    public AccountsPerPageDTO getAllByRole(String search, int currentPage, int limit, AccountRole role) throws DataAccessException {
        int accCount = accountRepository.getAllSearchedCount(search, role);
        int pageCount = accCount % limit == 0 ? accCount / limit : accCount / limit + 1;
        Collection<Account> accounts = accountRepository.getAllSearchedWithLimit(search, limit, (currentPage - 1) * limit, role);
        return new AccountsPerPageDTO(accounts, currentPage, pageCount);
    }

    @Override
    public AccountPersonalInfoDTO findById (long id) {
        Account account = accountRepository.findById(id);
        Credentials credentials = credentialsRepository.findById(id);
        AccountPersonalInfoDTO responseAcc = accountMapper.accountToAccountPersonalInfoDto(account);
        responseAcc.setEmail(credentials.getEmail());
        responseAcc.setStatus(!account.getAccountRole().equals(AccountRole.ROLE_BAN));
        return responseAcc;
    }
}
