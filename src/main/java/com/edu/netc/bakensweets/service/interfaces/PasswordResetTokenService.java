package com.edu.netc.bakensweets.service.interfaces;

import com.edu.netc.bakensweets.model.PasswordResetToken;
import com.edu.netc.bakensweets.model.payload.AuthRequestResetUpdatePassword;

public interface PasswordResetTokenService {
    void createToken(String email);
    PasswordResetToken generateToken(Long userId);
    boolean validateResetToken(String token);
    void changePassword(AuthRequestResetUpdatePassword authRequestResetUpdatePassword);
}
