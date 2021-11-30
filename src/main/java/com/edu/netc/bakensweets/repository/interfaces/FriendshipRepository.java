package com.edu.netc.bakensweets.repository.interfaces;

import com.edu.netc.bakensweets.model.Account;
import com.edu.netc.bakensweets.model.Friendship;
import com.edu.netc.bakensweets.model.FriendshipStatus;

import java.util.Collection;

public interface FriendshipRepository extends BaseCrudRepository<Friendship, Long> {
    void updateFriendshipStatus(long id, FriendshipStatus friendshipStatus);
    Collection<Account> findByFriendshipStatus(long inviterId, String search, int limit, int offset, FriendshipStatus friendshipStatus);
    Collection<Account> findFriendsToAdd(long inviterId, String search, int limit, int offset);
    Friendship findByInviterAndFriend(long inviterId, long friendId);
    int countByFriendshipStatus(long inviterId, String search,FriendshipStatus friendshipStatus);
    int countFriendsToAdd(long inviterId, String search);
}
