package com.edu.netc.bakensweets.service;

import com.edu.netc.bakensweets.dto.AccountPersonalInfoDTO;
import com.edu.netc.bakensweets.dto.PaginationDTO;
import com.edu.netc.bakensweets.exception.CustomException;
import com.edu.netc.bakensweets.mapperConfig.AccountMapper;
import com.edu.netc.bakensweets.model.Account;
import com.edu.netc.bakensweets.model.Friendship;
import com.edu.netc.bakensweets.model.FriendshipStatus;
import com.edu.netc.bakensweets.repository.interfaces.AccountRepository;
import com.edu.netc.bakensweets.repository.interfaces.FriendshipRepository;
import com.edu.netc.bakensweets.service.interfaces.FriendshipService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@AllArgsConstructor
@Service
public class FriendshipServiceImpl implements FriendshipService {
    private final AccountRepository accountRepository;
    private final FriendshipRepository friendshipRepository;
    private final AccountMapper accountMapper;

    @Transactional
    @Override
    public void createInvite(String inviterEmail, long friendId) {
        try {
            Account inviter = accountRepository.findByEmail(inviterEmail);
            if (friendshipRepository.findByInviterAndFriend(inviter.getId(), friendId)) {
                try {
                    friendshipRepository.create(new Friendship(inviter.getId(), friendId));
                } catch (DataIntegrityViolationException ex) {
                    throw new CustomException(HttpStatus.NOT_FOUND, String.format("Friend with friend id %s not found.", friendId));
                }
            } else {
                throw new CustomException(HttpStatus.NOT_FOUND, "Friendship already exists.");
            }
        } catch (DataAccessException ex) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Something wrong with server");
        }
    }


    @Transactional
    @Override
    public void deleteFriendship(String inviterEmail, long friendId) {
        try {
            Account inviter = accountRepository.findByEmail(inviterEmail);
            if (!friendshipRepository.deleteByInviterAndFriend(inviter.getId(), friendId)) {
                throw new CustomException(HttpStatus.NOT_FOUND, String.format("Friendship with friend id %s not found.", friendId));
            }
        } catch (DataAccessException ex) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Something wrong with server");
        }
    }

    @Transactional
    @Override
    public void acceptInvite(String inviterEmail, long friendId) {
        try {
            Account inviter = accountRepository.findByEmail(inviterEmail);
            if (!friendshipRepository.updateStatusByInviterAndFriend(FriendshipStatus.accepted, friendId, inviter.getId())) {
                throw new CustomException(HttpStatus.NOT_FOUND, String.format("Friendship with friend id %s not found.", friendId));
            }
        } catch (DataAccessException ex) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Something wrong with server");
        }
    }

    @Override
    @Transactional
    public void declineInvite(String inviterEmail, long friendId) {
        try {
            Account inviter = accountRepository.findByEmail(inviterEmail);
            if (!friendshipRepository.deleteByInviterAndFriend(friendId, inviter.getId())) {
                throw new CustomException(HttpStatus.NOT_FOUND, String.format("Friendship with friend id %s not found.", friendId));
            }
        } catch (DataAccessException ex) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Something wrong with server");
        }
    }


    @Override
    public PaginationDTO<AccountPersonalInfoDTO> getAllViableFriends(String inviterEmail, String search, String gender, int currentPage,
                                                                     int limit, boolean order) {
        Account account = accountRepository.findByEmail(inviterEmail);
        int personsSize = friendshipRepository.countFriendsToAdd(account.getId(), search, gender);
        Collection<Account> persons = friendshipRepository.findFriendsToAdd(account.getId(), search, gender, limit,
                currentPage * limit, order);
        return new PaginationDTO<>(accountMapper.accountsToPersonalInfoDtoCollection(persons), personsSize);
    }


    @Override
    public PaginationDTO<AccountPersonalInfoDTO> getInvites(String inviterEmail, String search, String gender, int currentPage, int limit, boolean order) {
        Account account = accountRepository.findByEmail(inviterEmail);
        int invitesSize = friendshipRepository.countByFriendshipUnaccepted(account.getId(), search, gender);
        Collection<Account> friends = friendshipRepository.findByFriendshipUnaccepted(account.getId(), search, gender, limit,
                currentPage * limit, order);
        return new PaginationDTO<>(accountMapper.accountsToPersonalInfoDtoCollection(friends), invitesSize);
    }


    @Override
    public PaginationDTO<AccountPersonalInfoDTO> getFriends(String inviterEmail, String search, String gender, int currentPage, int limit, boolean order) {
        Account account = accountRepository.findByEmail(inviterEmail);
        int friendsSize = friendshipRepository.countByFriendshipAccepted(account.getId(), search, gender);
        Collection<Account> friends = friendshipRepository.findByFriendshipAccepted(account.getId(), search, gender, limit,
                currentPage * limit, order);
        return new PaginationDTO<>(accountMapper.accountsToPersonalInfoDtoCollection(friends), friendsSize);
    }

}
