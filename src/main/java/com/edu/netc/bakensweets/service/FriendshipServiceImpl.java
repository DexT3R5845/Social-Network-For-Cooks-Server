package com.edu.netc.bakensweets.service;

import com.edu.netc.bakensweets.model.Account;
import com.edu.netc.bakensweets.model.Friendship;
import com.edu.netc.bakensweets.model.FriendshipStatus;
import com.edu.netc.bakensweets.repository.interfaces.AccountRepository;
import com.edu.netc.bakensweets.repository.interfaces.FriendshipRepository;
import com.edu.netc.bakensweets.service.interfaces.FriendshipService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FriendshipServiceImpl implements FriendshipService {
    private final AccountRepository accountRepository;
    private final FriendshipRepository friendshipRepository;


    @Override
    public String createInvite(String inviterEmail, long friendId) {
        Account inviterAcc = accountRepository.findByEmail(inviterEmail);
        friendshipRepository.create(new Friendship(inviterAcc.getId(),friendId));
        return "Invite has been sent";
    }

    @Override
    public String deleteFriendship(String inviterEmail, long friendId) {
        Account inviterAcc = accountRepository.findByEmail(inviterEmail);
        Friendship newFriendShip = friendshipRepository.findByInviterAndFriend(inviterAcc.getId(),friendId);
        friendshipRepository.deleteById(newFriendShip.getId());
        return "Friend removed";
    }

    @Override
    public String acceptInvite(String inviterEmail, long friendId) {
        Account inviterAcc = accountRepository.findByEmail(inviterEmail);
        Friendship newFriendShip = friendshipRepository.findByInviterAndFriend(inviterAcc.getId(),friendId);
        friendshipRepository.updateFriendshipStatus(newFriendShip.getId(), FriendshipStatus.accepted);
        return "Invite accepted";
    }


}
