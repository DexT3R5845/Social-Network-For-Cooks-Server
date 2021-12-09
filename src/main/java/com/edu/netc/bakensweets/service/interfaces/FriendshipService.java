package com.edu.netc.bakensweets.service.interfaces;

import com.edu.netc.bakensweets.dto.AccountPersonalInfoDTO;
import com.edu.netc.bakensweets.dto.PageDTO;

public interface FriendshipService {
    void createInvite(String inviterEmail, long friendId);
    void deleteFriendship(String inviterEmail, long friendId);
    void acceptInvite(String inviterEmail, long friendId);
    PageDTO<AccountPersonalInfoDTO> getAllViableFriends(String inviterEmail, String search, String gender, int currentPage, int limit,
                                                        boolean order);
    PageDTO<AccountPersonalInfoDTO> getInvites(String inviterEmail, String search, String gender, int currentPage, int limit, boolean order);
    PageDTO<AccountPersonalInfoDTO> getFriends(String inviterEmail, String search, String gender, int currentPage, int limit, boolean order);
}
