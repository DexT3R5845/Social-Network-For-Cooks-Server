package com.edu.netc.bakensweets.service;

import com.edu.netc.bakensweets.dto.NewModeratorDTO;
import com.edu.netc.bakensweets.exception.CustomException;
import com.edu.netc.bakensweets.mapper.AccountMapper;
import com.edu.netc.bakensweets.mapper.ModeratorMapper;
import com.edu.netc.bakensweets.model.Account;
import com.edu.netc.bakensweets.model.AccountRole;
import com.edu.netc.bakensweets.model.Credentials;
import com.edu.netc.bakensweets.model.UnconfirmedModerator;
import com.edu.netc.bakensweets.model.payload.AuthRequestResetUpdatePassword;
import com.edu.netc.bakensweets.repository.interfaces.AccountRepository;
import com.edu.netc.bakensweets.repository.interfaces.CredentialsRepository;
import com.edu.netc.bakensweets.repository.interfaces.UnconfirmedModerRepository;
import com.edu.netc.bakensweets.service.interfaces.EmailSenderService;
import com.edu.netc.bakensweets.service.interfaces.ModerCreationService;
import com.edu.netc.bakensweets.utils.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ModerCreationServiceImpl implements ModerCreationService {
    @Value("24")
    private Long expiration;

    CredentialsRepository credentialsRepository;
    AccountRepository accountRepository;
    UnconfirmedModerRepository moderRepository;
    AccountMapper accountMapper;
    ModeratorMapper moderatorMapper;
    EmailSenderService emailSenderService;
    PasswordEncoder passwordEncoder;

    public ModerCreationServiceImpl (CredentialsRepository credentialsRepository,
                                     UnconfirmedModerRepository moderRepository,
                                     AccountMapper accountMapper,
                                     ModeratorMapper moderatorMapper,
                                     EmailSenderService emailSenderService,
                                     PasswordEncoder passwordEncoder,
                                     AccountRepository accountRepository) {
        this.credentialsRepository = credentialsRepository;
        this.moderRepository = moderRepository;
        this.accountMapper = accountMapper;
        this.moderatorMapper = moderatorMapper;
        this.emailSenderService = emailSenderService;
        this.passwordEncoder = passwordEncoder;
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public void createToken(NewModeratorDTO moderatorDTO) {
        try {
            System.err.println("before checking");
            if (emailIsUnique(moderatorDTO.getEmail()) && hasNoActualExpiryDate(moderatorDTO.getEmail())) {
                System.err.println("after checking");
                UnconfirmedModerator moderator = getModerWithToken(moderatorDTO);
                System.err.println("before create");
                moderRepository.create(moderator);
                System.err.println("after create");
                emailSenderService.sendNewModerLinkPassword(moderator.getEmail(), moderator.getModerToken());
                System.err.println("after send email");
            }
        } catch (DataAccessException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public boolean validateModerToken(String token){
        UnconfirmedModerator moderator = moderRepository.getByToken(token);
        if (moderator != null) {
            if (moderator.getExpiryDate().isAfter(LocalDateTime.now())) {
                return true;
            }
            throw new CustomException(HttpStatus.GONE, "token is not valid");
        }
        throw new CustomException(HttpStatus.NOT_FOUND, "token not found");
    }

    @Override
    @Transactional
    public void createAccount(AuthRequestResetUpdatePassword authRequestResetUpdatePassword) {
        if (!validateModerToken(authRequestResetUpdatePassword.getToken())) {
            throw new CustomException(HttpStatus.GONE, "Invalid or expired token");
        }
        UnconfirmedModerator moderator = moderRepository.getByToken(authRequestResetUpdatePassword.getToken());
        if (moderator == null) throw new CustomException(HttpStatus.NOT_FOUND, "Moderator is not found");

        String password = passwordEncoder.encode(authRequestResetUpdatePassword.getPassword());
        Long id = Utils.generateUniqueId();

        credentialsRepository.create(new Credentials(id, moderator.getEmail(), password));
        Account account = moderatorMapper.unconfirmedModerToAccount(moderator);
        account.setAccountRole(AccountRole.ROLE_MODERATOR);
        account.setId(id);
        accountRepository.create(account);
    }

    private UnconfirmedModerator getModerWithToken (NewModeratorDTO moderatorDTO) {
        System.err.println("before mapping");
        UnconfirmedModerator moderator = moderatorMapper.newModerDTOtoUnconfirmedModer((moderatorDTO));
        moderator.setModerToken(Utils.stringGenerateUniqueId());
        moderator.setExpiryDate(LocalDateTime.now().plusHours(expiration));
        System.err.println("after mapping");
        return moderator;
    }

    private boolean emailIsUnique(String email) throws DataAccessException {
        System.err.println("in email checking");
        if (credentialsRepository.getCountEmailUsages(email) == 0) return true;
        else throw new CustomException(HttpStatus.CONFLICT, "email in not unique");
    }

    private boolean hasNoActualExpiryDate (String email)  throws DataAccessException {
        System.err.println("in expire checking");
        LocalDateTime expiryDate = moderRepository.findLatestExpiryDate(email);
        if ( expiryDate != null && expiryDate.isAfter(LocalDateTime.now())) {
            throw new CustomException(HttpStatus.FORBIDDEN, "this email has actual link that is valid until " + expiryDate);
        }
        return true;
    }
}
