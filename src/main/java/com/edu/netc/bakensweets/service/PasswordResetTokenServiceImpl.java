package com.edu.netc.bakensweets.service;

import com.edu.netc.bakensweets.exception.CustomException;
import com.edu.netc.bakensweets.model.Credentials;
import com.edu.netc.bakensweets.model.PasswordResetToken;
import com.edu.netc.bakensweets.model.payload.ValidateResetLink;
import com.edu.netc.bakensweets.repository.interfaces.CredentialsRepository;
import com.edu.netc.bakensweets.repository.interfaces.PasswordResetTokenRepository;
import com.edu.netc.bakensweets.utils.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService{
    private final PasswordResetTokenRepository passResetTokenRepository;
    private final CredentialsRepository credentialsRepository;
    private final EmailSenderService emailSenderService;
    @Value("60")
    private Long expiration;

    public PasswordResetTokenServiceImpl(PasswordResetTokenRepository passResetTokenRepository, CredentialsRepository credentialsRepository,
                                         EmailSenderService emailSenderService) {
        this.passResetTokenRepository = passResetTokenRepository;
        this.credentialsRepository = credentialsRepository;
        this.emailSenderService = emailSenderService;
    }

    @Override
    public void createToken(String email) {
        try {
            Credentials credentials = credentialsRepository.findByEmail(email);
            disableAllTokensByAccountId(credentials.getId());
            PasswordResetToken passwordResetToken = GenerateToken(credentials.getId());
            emailSenderService.sendResetLinkPassword(email, passwordResetToken.getResetToken());
            passResetTokenRepository.create(passwordResetToken);
        } catch (EmptyResultDataAccessException ex){
            throw new CustomException(String.format("Account %s not found.", email), HttpStatus.NOT_FOUND);
        }
    }

    public void disableAllTokensByAccountId(Long accountId){
            passResetTokenRepository.disableAllTokensByAccountId(accountId);
    }

    public ValidateResetLink validateResetToken(String token){
        try {
            PasswordResetToken passwordResetToken = passResetTokenRepository.findByToken(token);
            boolean expiry = passwordResetToken.isActive() && passwordResetToken.getExpiryDate().isAfter(LocalDateTime.now());
            return new ValidateResetLink(expiry);
        } catch (EmptyResultDataAccessException ex){
            throw new CustomException(String.format("Token %s not found", token), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public PasswordResetToken GenerateToken(Long userId) {
        return new PasswordResetToken(Utils.stringGenerateUniqueId(),
                LocalDateTime.now().plusMinutes(expiration),
                userId,
                true);
    }
}
