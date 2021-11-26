package com.edu.netc.bakensweets.service.interfaces;

import com.edu.netc.bakensweets.model.Friendship;
import org.springframework.web.bind.annotation.PostMapping;

public interface FriendshipService {
    String createInvite(String inviterEmail, long friendId);
    String deleteFriendship(String inviterEmail, long friendId);
    String acceptInvite(String inviterEmail, long friendId);
}
