package com.edu.netc.bakensweets.repository.interfaces;

import com.edu.netc.bakensweets.model.UnconfirmedModerator;

import java.time.LocalDateTime;

public interface UnconfirmedModerRepository extends BaseCrudRepository<UnconfirmedModerator, Integer> {
    UnconfirmedModerator getByToken(String token);
    LocalDateTime findLatestExpiryDate(String email);
}
