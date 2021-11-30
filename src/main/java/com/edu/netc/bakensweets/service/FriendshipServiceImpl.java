package com.edu.netc.bakensweets.service;

import com.edu.netc.bakensweets.dto.AccountsPerPageDTO;
import com.edu.netc.bakensweets.model.Account;
import com.edu.netc.bakensweets.model.Friendship;
import com.edu.netc.bakensweets.model.FriendshipStatus;
import com.edu.netc.bakensweets.repository.interfaces.AccountRepository;
import com.edu.netc.bakensweets.repository.interfaces.FriendshipRepository;
import com.edu.netc.bakensweets.service.interfaces.FriendshipService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@AllArgsConstructor
@Service
public class FriendshipServiceImpl implements FriendshipService {
    private final AccountRepository accountRepository;
    private final FriendshipRepository friendshipRepository;

    public int countPage ( int size, int limit){
        return size % limit == 0 ? size / limit : size/ limit + 1;
    }

    @Override
    public void createInvite(String inviterEmail, long friendId) {
        Account inviterAcc = accountRepository.findByEmail(inviterEmail);
        friendshipRepository.create(new Friendship(inviterAcc.getId(),friendId));
    }

    @Override
    public void deleteFriendship(String inviterEmail, long friendId) {
        Account inviterAcc = accountRepository.findByEmail(inviterEmail);
        Friendship newFriendShip = friendshipRepository.findByInviterAndFriend(inviterAcc.getId(),friendId);
        friendshipRepository.deleteById(newFriendShip.getId());

    }

    @Override
    public void acceptInvite(String inviterEmail, long friendId) {
        Account inviterAcc = accountRepository.findByEmail(inviterEmail);
        Friendship newFriendShip = friendshipRepository.findByInviterAndFriend(inviterAcc.getId(),friendId);
        friendshipRepository.updateFriendshipStatus(newFriendShip.getId(), FriendshipStatus.accepted);
    }

    @Override
    public AccountsPerPageDTO getAllViableFriends(String inviterEmail,String search, int currentPage, int limit) {
        Account sessionAcc = accountRepository.findByEmail(inviterEmail);
        int personsSize = friendshipRepository.countFriendsToAdd(sessionAcc.getId(), search);
        int pageCount = countPage(personsSize, limit);
        Collection<Account> persons = friendshipRepository.findFriendsToAdd(sessionAcc.getId(),search, limit,
                (currentPage - 1) * limit);
        return new AccountsPerPageDTO(persons, currentPage, pageCount);
    }

    @Override
    public AccountsPerPageDTO getInvites(String inviterEmail, String search, int currentPage, int limit) {
        Account sessionAcc = accountRepository.findByEmail(inviterEmail);
        int invitesSize = friendshipRepository.countByFriendshipStatus(sessionAcc.getId(), search, FriendshipStatus.unaccepted);
        int pageCount = countPage(invitesSize, limit);
        Collection<Account> friends = friendshipRepository.findByFriendshipStatus(sessionAcc.getId(),search, limit,
                (currentPage - 1) * limit, FriendshipStatus.unaccepted);
        return new AccountsPerPageDTO(friends, currentPage, pageCount);
    }

    @Override
    public AccountsPerPageDTO getFriends(String inviterEmail, String search, int currentPage, int limit) {
        Account sessionAcc = accountRepository.findByEmail(inviterEmail);
        int friendsSize = friendshipRepository.countByFriendshipStatus(sessionAcc.getId(), search, FriendshipStatus.accepted);
        int pageCount = countPage(friendsSize, limit);;
        Collection<Account> friends = friendshipRepository.findByFriendshipStatus(sessionAcc.getId(),search, limit,
                (currentPage - 1) * limit, FriendshipStatus.accepted);
        return new AccountsPerPageDTO(friends, currentPage, pageCount);
    }






}
