package com.edu.netc.bakensweets.service;

import com.edu.netc.bakensweets.dto.AccountDTO;
import com.edu.netc.bakensweets.dto.AccountPersonalInfoDTO;
import com.edu.netc.bakensweets.dto.AccountsPerPageDTO;
import com.edu.netc.bakensweets.dto.UpdateAccountDTO;
import com.edu.netc.bakensweets.exception.BadRequestParamException;
import com.edu.netc.bakensweets.exception.CustomException;
import com.edu.netc.bakensweets.exception.FailedAuthorizationException;
import com.edu.netc.bakensweets.mapperConfig.AccountMapper;
import com.edu.netc.bakensweets.mapperConfig.CredentialsMapper;
import com.edu.netc.bakensweets.model.*;
import com.edu.netc.bakensweets.repository.interfaces.AccountRepository;
import com.edu.netc.bakensweets.repository.interfaces.CredentialsRepository;
import com.edu.netc.bakensweets.security.JwtTokenProvider;
import com.edu.netc.bakensweets.service.interfaces.AccountService;
import com.edu.netc.bakensweets.service.interfaces.CaptchaService;
import com.edu.netc.bakensweets.service.interfaces.WrongAttemptLoginService;
import com.edu.netc.bakensweets.utils.Utils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;


import java.time.LocalDateTime;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final CredentialsRepository credentialsRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final CredentialsMapper credentialsMapper;
    private final AccountMapper accountMapper;
    private final WrongAttemptLoginService wrongAttemptLoginService;
    private final CaptchaService captchaService;

    public AccountServiceImpl(AccountRepository accountRepository, CredentialsRepository credentialsRepository, JwtTokenProvider jwtTokenProvider,
                              AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, CredentialsMapper credentialsMapper,
                              AccountMapper accountMapper, WrongAttemptLoginService wrongAttemptLoginService, CaptchaService captchaService) {
        this.accountRepository = accountRepository;
        this.credentialsRepository = credentialsRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.credentialsMapper = credentialsMapper;
        this.accountMapper = accountMapper;
        this.wrongAttemptLoginService = wrongAttemptLoginService;
        this.captchaService = captchaService;
    }

    @Override
    public String signIn(String username, String password, String recaptcha_token, String ip) {
        boolean need_captcha = false;
        WrongAttemptLogin sessionUserWrongAttemt = wrongAttemptLoginService.findSessionByIpAndTime(ip, LocalDateTime.now());
        try {
            if(sessionUserWrongAttemt != null && sessionUserWrongAttemt.getCountWrongAttempts() >= 5) {
                need_captcha = true;
                if(recaptcha_token == null || recaptcha_token.isEmpty())
                    throw new FailedAuthorizationException(HttpStatus.UNPROCESSABLE_ENTITY, "Need captcha", need_captcha);
                if(!captchaService.isValidCaptcha(recaptcha_token)) {
                    wrongAttemptLoginService.UpdateSession(sessionUserWrongAttemt);
                    throw new FailedAuthorizationException(HttpStatus.UNPROCESSABLE_ENTITY, "Recaptcha token is invalid", need_captcha);
                }
            }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return jwtTokenProvider.createToken(username, accountRepository.findByEmail(username).getAccountRole());
        } catch (AuthenticationException e) {
            if(sessionUserWrongAttemt == null)
                wrongAttemptLoginService.CreateSession(new WrongAttemptLogin(ip, LocalDateTime.now(), 1));
            else
                wrongAttemptLoginService.UpdateSession(sessionUserWrongAttemt);
            throw new FailedAuthorizationException(HttpStatus.UNAUTHORIZED, "Invalid username/password supplied", need_captcha);
        }
    }

    @Override
    public String signUp(AccountDTO accountDTO) {
        try {
            createNewAccount(accountDTO, AccountRole.ROLE_USER);
            return "Reg Success";
        } catch (DuplicateKeyException ex) {
            throw new BadRequestParamException("email", "Email already exists", "EMAIL_EXIST");
        }
    }

    private void createNewAccount(AccountDTO accountDTO, AccountRole accountRole){
        long uniqueId = Utils.generateUniqueId();

        Credentials credentials = credentialsMapper.accountDTOtoCredentials(accountDTO);
        Account account = accountMapper.accountDTOtoAccount(accountDTO);

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
            accountRepository.updateStatus(id, !status);
        } catch (DataAccessException ex) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "There is no account with such id");
        }
    }

    @Override
    public UpdateAccountDTO getUserInfoByEmail(String email) {
        try {
            Account account = accountRepository.findByEmail(email);
            return accountMapper.accountToUpdateAccountDTO(account);
        }catch (EmptyResultDataAccessException ex){throw new CustomException(HttpStatus.NOT_FOUND, "Account not found");}
    }

    @Override
    public AccountsPerPageDTO getAllBySearchAccounts(String search, int currentPage, int limit,
                                                     boolean order, String gender) {
        return getAllBySearch(search, currentPage, limit, AccountRole.ROLE_USER, order, gender, "true");
    }


    @Override
    public AccountsPerPageDTO getAllBySearchModerators(String search, int currentPage, int limit,
                                                       boolean order, String gender, String status) {
        return getAllBySearch(search, currentPage, limit, AccountRole.ROLE_MODERATOR, order, gender, status);
    }


    public AccountsPerPageDTO getAllBySearch (String search, int currentPage, int limit, AccountRole role,
                                              boolean order, String gender, String status) {
        int accCount = accountRepository.countAccountsBySearch(search, role, gender, status);
        Collection<Account> accounts = accountRepository.findAccountsBySearch(
                search, gender, role, status, limit,  currentPage * limit, order
        );
        return new AccountsPerPageDTO(
                accountMapper.accountsToPersonalInfoDtoCollection(accounts), currentPage,  accCount
        );
    }


    @Override
    public AccountPersonalInfoDTO findById (long id) {
        Account account = accountRepository.findById(id);
        Credentials credentials = credentialsRepository.findById(id);

        if (account == null || credentials == null) {
            throw new CustomException(HttpStatus.NOT_FOUND, "no accounts found with such id");
        }
        AccountPersonalInfoDTO responseAcc = accountMapper.accountToAccountPersonalInfoDto(account);
        responseAcc.setEmail(credentials.getEmail());
        return responseAcc;
    }
}
