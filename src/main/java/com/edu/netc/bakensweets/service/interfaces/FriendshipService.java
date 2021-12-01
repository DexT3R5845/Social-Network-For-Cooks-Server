package com.edu.netc.bakensweets.service.interfaces;

import com.edu.netc.bakensweets.dto.AccountsPerPageDTO;
import com.edu.netc.bakensweets.model.Friendship;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collection;

public interface FriendshipService {
    void createInvite(String inviterEmail, long friendId);
    void deleteFriendship(String inviterEmail, long friendId);
    void acceptInvite(String inviterEmail, long friendId);
    AccountsPerPageDTO getAllViableFriends(String inviterEmail, String search, String gender, int currentPage, int limit,
                                           boolean order);
    AccountsPerPageDTO getInvites(String inviterEmail, String search, String gender, int currentPage, int limit, boolean order);
    AccountsPerPageDTO getFriends(String inviterEmail, String search, String gender, int currentPage, int limit, boolean order);
}
