package com.edu.netc.bakensweets.service;

import com.edu.netc.bakensweets.dto.account.NewModeratorDTO;
import com.edu.netc.bakensweets.exception.CustomException;
import com.edu.netc.bakensweets.mapperConfig.AccountMapper;
import com.edu.netc.bakensweets.model.*;
import com.edu.netc.bakensweets.model.payload.AuthRequestResetUpdatePassword;
import com.edu.netc.bakensweets.repository.interfaces.AccountRepository;
import com.edu.netc.bakensweets.repository.interfaces.CredentialsRepository;
import com.edu.netc.bakensweets.repository.interfaces.UnconfirmedModerRepository;
import com.edu.netc.bakensweets.service.email.EmailSenderService;
import com.edu.netc.bakensweets.service.interfaces.ModerCreationService;
import com.edu.netc.bakensweets.utils.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ModerCreationServiceImpl implements ModerCreationService {
    @Value("24")
    private Long expiration;

    CredentialsRepository credentialsRepository;
    AccountRepository accountRepository;
    UnconfirmedModerRepository moderRepository;
    AccountMapper accountMapper;
    EmailSenderService emailSenderService;
    PasswordEncoder passwordEncoder;

    public ModerCreationServiceImpl (CredentialsRepository credentialsRepository,
                                     UnconfirmedModerRepository moderRepository,
                                     AccountMapper accountMapper,
                                     EmailSenderService emailSenderService,
                                     PasswordEncoder passwordEncoder,
                                     AccountRepository accountRepository) {
        this.credentialsRepository = credentialsRepository;
        this.moderRepository = moderRepository;
        this.accountMapper = accountMapper;
        this.emailSenderService = emailSenderService;
        this.passwordEncoder = passwordEncoder;
        this.accountRepository = accountRepository;
    }

    @Override
    public void createToken(NewModeratorDTO moderatorDTO) {
        if (checkUniqueEmail(moderatorDTO.getEmail())) {
            UnconfirmedModerator moderator = getModerWithToken(moderatorDTO);
            moderRepository.create(moderator);
            emailSenderService.sendNewModerLinkPassword(moderator.getEmail(), moderator.getModerToken());
        }
        else throw new CustomException(HttpStatus.CONFLICT, String.format("email in not unique"));
    }

    @Override
    public HttpStatus validateModerToken(String token){
        try {
            UnconfirmedModerator moderator = moderRepository.getByToken(token);
            return moderator.getExpireDate().isAfter(LocalDateTime.now()) ? HttpStatus.OK : HttpStatus.GONE;

        } catch (DataAccessException ex) {
            throw new CustomException(HttpStatus.NOT_FOUND, String.format("row is not found in db", token));
        }
    }

    @Override
    public HttpStatus createAccount(AuthRequestResetUpdatePassword authRequestResetUpdatePassword) {
        try {
            if (!validateModerToken(authRequestResetUpdatePassword.getToken()).is2xxSuccessful()) {
                return HttpStatus.GONE;
            }
            UnconfirmedModerator moderator = moderRepository.getByToken(authRequestResetUpdatePassword.getToken());
            String password = passwordEncoder.encode(authRequestResetUpdatePassword.getPassword());
            Long id = Utils.generateUniqueId();

            credentialsRepository.create(new Credentials(id, moderator.getEmail(), password));
            Account account = accountMapper.unconfirmedModerToAccount(moderator);
            account.setAccountRole(AccountRole.ROLE_MODERATOR);
            account.setId(id);
            accountRepository.create(account);

            return HttpStatus.OK;

        } catch (DataAccessException ex) {
            throw new CustomException(HttpStatus.NOT_FOUND, String.format("Got some troubles"));
        }
    }


    private UnconfirmedModerator getModerWithToken (NewModeratorDTO moderatorDTO) {
        UnconfirmedModerator moderator = accountMapper.newModerDTOtoUnconfirmedModer((moderatorDTO));
        moderator.setModerToken(Utils.stringGenerateUniqueId());
        moderator.setExpireDate(LocalDateTime.now().plusHours(expiration));
        return moderator;
    }

    private boolean checkUniqueEmail(String email) {
        try {
            Credentials credentials = credentialsRepository.findByEmail(email);
            if (credentials != null) return false;
        } catch (DataAccessException ex) {}
        return true;
    }
}
