package com.edu.netc.bakensweets.service;

import com.edu.netc.bakensweets.model.PasswordResetToken;

public interface PasswordResetTokenService {
    void createToken(String email);
    PasswordResetToken GenerateToken(Long userId);
}
