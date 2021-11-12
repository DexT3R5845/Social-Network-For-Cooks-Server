package com.edu.netc.bakensweets.service;

import com.edu.netc.bakensweets.model.Account;
import com.edu.netc.bakensweets.model.Credentials;
import com.edu.netc.bakensweets.repository.interfaces.AccountRepository;
import com.edu.netc.bakensweets.repository.interfaces.CredentialsRepository;
import com.edu.netc.bakensweets.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

public AccountServiceImpl(AccountRepository accountRepository, CredentialsRepository credentialsRepository,JwtTokenProvider jwtTokenProvider,
                          AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder){
    this.accountRepository = accountRepository;
    this.credentialsRepository = credentialsRepository;
    this.jwtTokenProvider = jwtTokenProvider;
    this.authenticationManager = authenticationManager;
    this.passwordEncoder = passwordEncoder;
}
    @Override
    public String signin(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return jwtTokenProvider.createToken(username, accountRepository.findByEmail(username).getAccountRole());
        } catch (AuthenticationException e) {
            throw new com.edu.netc.bakensweets.exception.CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    public String signUp() {
        return null;
    }

    public Account getByEmail(String email) {
        return accountRepository.findByEmail(email);
    }
}
