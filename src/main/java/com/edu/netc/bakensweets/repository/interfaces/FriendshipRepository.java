package com.edu.netc.bakensweets.repository.interfaces;

import com.edu.netc.bakensweets.model.Account;
import com.edu.netc.bakensweets.model.Friendship;
import com.edu.netc.bakensweets.model.FriendshipStatus;

import java.util.Collection;

public interface FriendshipRepository extends BaseCrudRepository<Friendship, Long> {
    Collection<Account> findByFriendshipAccepted(long inviterId, String search, String gender, int limit, int offset,
                                                 boolean order);
    Collection<Account> findByFriendshipUnaccepted(long inviterId, String search, String gender, int limit, int offset,
                                                 boolean order);
    Collection<Account> findFriendsToAdd(long inviterId, String search, String gender, int limit, int offset,
                                         boolean order);
    Friendship findByInviterAndFriend(long inviterId, long friendId);
    int countByFriendshipAccepted(long inviterId, String search, String gender);
    int countByFriendshipUnaccepted(long inviterId, String search, String gender);
    int countFriendsToAdd(long inviterId, String search,String gender);
}
