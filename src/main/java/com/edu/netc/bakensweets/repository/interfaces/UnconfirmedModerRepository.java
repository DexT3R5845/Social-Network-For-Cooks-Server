package com.edu.netc.bakensweets.repository.interfaces;

import com.edu.netc.bakensweets.model.UnconfirmedModerator;

public interface UnconfirmedModerRepository extends BaseCrudRepository<UnconfirmedModerator, Integer> {
    UnconfirmedModerator getByToken(String token);
    Integer findUsagesOfEmail(String email);
}
