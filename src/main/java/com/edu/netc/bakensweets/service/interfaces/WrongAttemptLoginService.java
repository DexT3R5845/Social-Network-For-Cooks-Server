package com.edu.netc.bakensweets.service.interfaces;

import com.edu.netc.bakensweets.model.WrongAttemptLogin;

import java.time.LocalDateTime;

public interface WrongAttemptLoginService {
    WrongAttemptLogin findSessionByIpAndTime(String ip, LocalDateTime time);
    void createSession(WrongAttemptLogin wrongAttemptLogin);
    void updateSession(WrongAttemptLogin wrongAttemptLogin);
}
