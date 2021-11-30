package com.edu.netc.bakensweets.repository.interfaces;

import com.edu.netc.bakensweets.model.Kitchenware;
import com.edu.netc.bakensweets.model.KitchenwareCategory;

import java.util.Collection;

public interface KitchenwareRepository extends  BaseCrudRepository<Kitchenware, Long>{
//    void updateFriendshipStatus(long id, FriendshipStatus friendshipStatus);
//    Collection<Friendship> findByFriendshipStatus(FriendshipStatus friendshipStatus);
//    Friendship findByInviterAndFriend(long inviterId, long friendId);
    Collection<KitchenwareCategory> getAllCategories();
}
