package com.edu.netc.bakensweets.repository.interfaces;

import com.edu.netc.bakensweets.model.Friendship;
import com.edu.netc.bakensweets.model.FriendshipStatus;

import java.util.Collection;

public interface FriendshipRepository extends BaseCrudRepository<Friendship, Integer> {
    void updateFriendshipStatus(long id, FriendshipStatus friendshipStatus);
    Collection<Friendship> findByFriendshipStatus(FriendshipStatus friendshipStatus);
    Friendship findByInviterAndFriend(long inviterId, long friendId);
}
