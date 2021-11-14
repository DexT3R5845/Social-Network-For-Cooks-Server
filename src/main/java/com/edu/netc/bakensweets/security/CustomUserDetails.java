package com.edu.netc.bakensweets.security;

import com.edu.netc.bakensweets.model.Account;
import com.edu.netc.bakensweets.model.Credentials;
import com.edu.netc.bakensweets.repository.interfaces.AccountRepository;
import com.edu.netc.bakensweets.repository.interfaces.CredentialsRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetails implements UserDetailsService {

  private final AccountRepository accountRepository;
  private final CredentialsRepository credentialsRepository;

  public CustomUserDetails(AccountRepository accountRepository, CredentialsRepository credentialsRepository){
    this.accountRepository = accountRepository;
    this.credentialsRepository = credentialsRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    final Credentials credentials = credentialsRepository.findByEmail(username);
    final Account account = accountRepository.findById(credentials.getId());
    if (account == null) {
      throw new UsernameNotFoundException("User '" + username + "' not found");
    }
    return org.springframework.security.core.userdetails.User//
        .withUsername(username)
        .password(credentials.getPassword())
        .authorities(account.getAccountRole())
        .build();
  }

}