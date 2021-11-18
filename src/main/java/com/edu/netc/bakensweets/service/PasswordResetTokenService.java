package com.edu.netc.bakensweets.service;

import com.edu.netc.bakensweets.model.PasswordResetToken;
import com.edu.netc.bakensweets.model.payload.ValidateResetLink;

public interface PasswordResetTokenService {
    void createToken(String email);
    PasswordResetToken GenerateToken(Long userId);
    ValidateResetLink validateResetToken(String token);
}
