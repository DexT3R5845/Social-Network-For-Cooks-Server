package com.edu.netc.bakensweets.security;

import com.edu.netc.bakensweets.model.Account;
import com.edu.netc.bakensweets.repository.interfaces.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetailsService {

  private final AccountRepository accountRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    final Account account = accountRepository.findByEmail(username);
    if (account == null) {
      throw new UsernameNotFoundException("User '" + username + "' not found");
    }
    return org.springframework.security.core.userdetails.User//
        .withUsername(username)
        .password(account.getPassword())
        .authorities(account.getAccountRole())
        .accountExpired(false)
        .accountLocked(false)
        .disabled(false)
        .build();
  }

}
