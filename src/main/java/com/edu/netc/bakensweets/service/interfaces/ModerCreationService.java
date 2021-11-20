package com.edu.netc.bakensweets.service.interfaces;

import com.edu.netc.bakensweets.dto.account.NewModeratorDTO;
import com.edu.netc.bakensweets.model.payload.AuthRequestResetUpdatePassword;
import org.springframework.http.HttpStatus;

public interface ModerCreationService {
    void createToken(NewModeratorDTO moderatorDTO);
    HttpStatus validateModerToken(String token);
    HttpStatus createAccount(AuthRequestResetUpdatePassword authRequestResetUpdatePassword);
}
