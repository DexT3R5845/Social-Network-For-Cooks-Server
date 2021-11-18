package com.edu.netc.bakensweets.repository.interfaces;

import com.edu.netc.bakensweets.model.PasswordResetToken;

public interface PasswordResetTokenRepository extends BaseCrudRepository<PasswordResetToken, Long>{
    PasswordResetToken findByAccountId(Long id);
    void disableAllTokensByAccountId(Long id);
    PasswordResetToken findByToken(String token);
}
