package com.edu.netc.bakensweets.repository.interfaces;

import com.edu.netc.bakensweets.model.WrongAttemptLogin;

import java.time.LocalDateTime;

public interface WrongAttempLoginRepository extends BaseCrudRepository<WrongAttemptLogin,Long>{
    WrongAttemptLogin findByIpAndTime(String ip, LocalDateTime time);
}
