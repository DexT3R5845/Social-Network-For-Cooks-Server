package com.edu.netc.bakensweets.repository.interfaces;

import com.edu.netc.bakensweets.model.Credentials;

public interface CredentialsRepository extends BaseCrudRepository<Credentials, Long> {
    Credentials findByEmail(String email);
    void insertJwtToken(String token, String email);
}
