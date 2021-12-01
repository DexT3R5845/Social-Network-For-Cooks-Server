package com.edu.netc.bakensweets.service;

import com.edu.netc.bakensweets.dto.AccountPersonalInfoDTO;
import com.edu.netc.bakensweets.dto.AccountsPerPageDTO;
import com.edu.netc.bakensweets.exception.CustomException;
import com.edu.netc.bakensweets.model.Account;
import com.edu.netc.bakensweets.model.Friendship;
import com.edu.netc.bakensweets.model.FriendshipStatus;
import com.edu.netc.bakensweets.repository.interfaces.AccountRepository;
import com.edu.netc.bakensweets.repository.interfaces.FriendshipRepository;
import com.edu.netc.bakensweets.service.interfaces.FriendshipService;
import com.edu.netc.bakensweets.mapperConfig.AccountMapper;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collection;

@AllArgsConstructor
@Service
public class FriendshipServiceImpl implements FriendshipService {
    private final AccountRepository accountRepository;
    private final FriendshipRepository friendshipRepository;
    private final AccountMapper accountMapper;

    public int countPage ( int size, int limit){
        return size % limit == 0 ? size / limit : size/ limit + 1;
    }

    @Override
    public void createInvite(String inviterEmail, long friendId) {
        Account inviterAcc = accountRepository.findByEmail(inviterEmail);
        try {
            friendshipRepository.create(new Friendship(inviterAcc.getId(), friendId));
        }catch (DataAccessException ex) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "There is no account with such id");
        }
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
        try {
            Friendship newFriendShip = friendshipRepository.findByInviterAndFriend(inviterAcc.getId(),friendId);
            friendshipRepository.updateFriendshipStatus(newFriendShip.getId(), FriendshipStatus.accepted);
        }catch (DataAccessException ex) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "There is no account with such id");
        }
    }

    @Override
    public AccountsPerPageDTO getAllViableFriends(String inviterEmail, String search, String gender, int currentPage,
                                                  int limit, boolean order) {
        Account sessionAcc = accountRepository.findByEmail(inviterEmail);
        int personsSize = friendshipRepository.countFriendsToAdd(sessionAcc.getId(), search,gender);
        int pageCount = countPage(personsSize, limit);
        Collection<Account> persons = friendshipRepository.findFriendsToAdd(sessionAcc.getId(),search,gender, limit,
                (currentPage - 1) * limit, order);
        return new AccountsPerPageDTO(accountMapper.accountsToPersonalInfoDtoCollection(persons), currentPage, pageCount);
    }

    @Override
    public AccountsPerPageDTO getInvites(String inviterEmail, String search, String gender, int currentPage, int limit, boolean order) {
        Account sessionAcc = accountRepository.findByEmail(inviterEmail);
        int invitesSize = friendshipRepository.countByFriendshipUnaccepted(sessionAcc.getId(), search, gender);
        int pageCount = countPage(invitesSize, limit);
        Collection<Account> friends = friendshipRepository.findByFriendshipUnaccepted(sessionAcc.getId(),search, gender, limit,
                (currentPage - 1) * limit, order);
        return new AccountsPerPageDTO(accountMapper.accountsToPersonalInfoDtoCollection(friends), currentPage, pageCount);
    }

    @Override
    public AccountsPerPageDTO getFriends(String inviterEmail, String search, String gender, int currentPage, int limit, boolean order) {
        Account sessionAcc = accountRepository.findByEmail(inviterEmail);
        int friendsSize = friendshipRepository.countByFriendshipAccepted(sessionAcc.getId(), search, gender);
        int pageCount = countPage(friendsSize, limit);;
        Collection<Account> friends = friendshipRepository.findByFriendshipAccepted(sessionAcc.getId(),search, gender, limit,
                (currentPage - 1) * limit, order);
        return new AccountsPerPageDTO(accountMapper.accountsToPersonalInfoDtoCollection(friends), currentPage, pageCount);
    }






}
