package com.edu.netc.bakensweets.service.interfaces;

import com.edu.netc.bakensweets.dto.NewModeratorDTO;
import com.edu.netc.bakensweets.model.payload.AuthRequestResetUpdatePassword;

public interface ModerCreationService {
    String createToken(NewModeratorDTO moderatorDTO);
    boolean validateModerToken(String token);
    void createAccount(AuthRequestResetUpdatePassword authRequestResetUpdatePassword);
}
