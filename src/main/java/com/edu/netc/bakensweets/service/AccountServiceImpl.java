package com.edu.netc.bakensweets.service;

import com.edu.netc.bakensweets.dto.AccountDTO;
import com.edu.netc.bakensweets.exception.BadRequestParamException;
import com.edu.netc.bakensweets.exception.CustomException;
import com.edu.netc.bakensweets.mapperConfig.AccountMapper;
import com.edu.netc.bakensweets.mapperConfig.CredentialsMapper;
import com.edu.netc.bakensweets.model.Account;
import com.edu.netc.bakensweets.model.AccountRole;
import com.edu.netc.bakensweets.model.Credentials;
import com.edu.netc.bakensweets.model.WrongAttemptLogin;
import com.edu.netc.bakensweets.repository.interfaces.AccountRepository;
import com.edu.netc.bakensweets.repository.interfaces.CredentialsRepository;
import com.edu.netc.bakensweets.security.JwtTokenProvider;
import com.edu.netc.bakensweets.utils.Utils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

public AccountServiceImpl(AccountRepository accountRepository, CredentialsRepository credentialsRepository,JwtTokenProvider jwtTokenProvider,
                          AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, CredentialsMapper credentialsMapper,
                          AccountMapper accountMapper, WrongAttemptLoginService wrongAttemptLoginService, CaptchaService captchaService){
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
        WrongAttemptLogin sessionUserWrongAttemt = wrongAttemptLoginService.findSessionByIpAndTime(ip, LocalDateTime.now());
        try {
            if(sessionUserWrongAttemt != null && sessionUserWrongAttemt.getCountWrongAttempts() >= 5
            && recaptcha_token != null && !recaptcha_token.isEmpty() && !captchaService.isValidCaptcha(recaptcha_token)) {
                wrongAttemptLoginService.UpdateSession(sessionUserWrongAttemt);
                throw new CustomException(HttpStatus.UNPROCESSABLE_ENTITY, "Need captcha response");
            }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return jwtTokenProvider.createToken(username, accountRepository.findByEmail(username).getAccountRole());
        } catch (AuthenticationException e) {
            if(sessionUserWrongAttemt == null)
                wrongAttemptLoginService.CreateSession(new WrongAttemptLogin(ip, LocalDateTime.now(), 1));
            else
                wrongAttemptLoginService.UpdateSession(sessionUserWrongAttemt);
            throw new CustomException(HttpStatus.UNAUTHORIZED, "Invalid username/password supplied");
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
