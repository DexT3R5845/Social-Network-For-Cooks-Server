package com.edu.netc.bakensweets.service.interfaces;

import com.edu.netc.bakensweets.model.PasswordResetToken;
import com.edu.netc.bakensweets.model.payload.AuthRequestResetUpdatePassword;
import com.edu.netc.bakensweets.model.payload.ValidateResetLink;

public interface PasswordResetTokenService {
    void createToken(String email);
    PasswordResetToken GenerateToken(Long userId);
    ValidateResetLink validateResetToken(String token);
    void changePassword(AuthRequestResetUpdatePassword authRequestResetUpdatePassword);
}
