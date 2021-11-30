package com.edu.netc.bakensweets.repository;

import com.edu.netc.bakensweets.model.Account;
import com.edu.netc.bakensweets.model.Friendship;
import com.edu.netc.bakensweets.model.FriendshipStatus;
import com.edu.netc.bakensweets.repository.interfaces.FriendshipRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class FriendshipRepositoryImpl extends BaseJdbsRepository implements FriendshipRepository {
    @Value("${sql.friendship.create}")
    private String sqlCreate;
    @Value("${sql.friendship.delete}")
    private String sqlDelete;
    @Value("${sql.friendship.findByInviterAndFriend}")
    private String sqlFindByInviterAndFriend;
    @Value("${sql.friendship.updateFriendshipStatus}")
    private String sqlUpdateFriendshipStatus;
    @Value("${sql.friendship.findByFriendshipStatus}")
    private String sqlFindByFriendshipStatus;
    @Value("${sql.friendship.countByFriendshipStatus}")
    private String sqlCountByFriendshipStatus;
    @Value("${sql.friendship.findAllNotFriends}")
    private String sqlFindAllNotFriends;
    @Value("${sql.friendship.countAllNotFriends}")
    private String sqlCountAllNotFriends;

    public FriendshipRepositoryImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void updateFriendshipStatus(long id, FriendshipStatus friendshipStatus) {
        jdbcTemplate.update(sqlUpdateFriendshipStatus, friendshipStatus.toString(), id);
    }

    @Override
    public Collection<Account> findByFriendshipStatus(long inviterId, String search, int limit, int offset, FriendshipStatus friendshipStatus) {
        return jdbcTemplate.query(sqlFindByFriendshipStatus,
                new BeanPropertyRowMapper<>(Account.class), inviterId, inviterId, inviterId, friendshipStatus.toString(),
                search, search, limit, offset);
    }


    @Override
    public Collection<Account> findFriendsToAdd(long inviterId, String search, int limit, int offset) {
        return jdbcTemplate.query(sqlFindAllNotFriends,
                new BeanPropertyRowMapper<>(Account.class), inviterId, inviterId, inviterId, search, search, limit, offset);
    }

    @Override
    public Friendship findByInviterAndFriend(long inviterId, long friendId) {
        return jdbcTemplate.queryForObject(sqlFindByInviterAndFriend,
                new BeanPropertyRowMapper<>(Friendship.class), inviterId, friendId);
    }

    @Override
    public int countByFriendshipStatus(long inviterId, String search, FriendshipStatus friendshipStatus) {
        Integer count = jdbcTemplate.queryForObject(sqlCountByFriendshipStatus,Integer.class, inviterId, inviterId, inviterId,
                friendshipStatus.toString(), search, search);
        return count == null ? 0 : count;
    }

    @Override
    public int countFriendsToAdd(long inviterId, String search) {
        Integer count = jdbcTemplate.queryForObject(sqlCountAllNotFriends,Integer.class, inviterId, inviterId, inviterId, search, search);
        System.out.println(count);
        return count == null ? 0 : count;
    }

    @Override
    public void create(Friendship friendship) {
        jdbcTemplate.update(sqlCreate, friendship.getInviterId(), friendship.getFriendId());
    }

    @Override
    public void update(Friendship item) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update(sqlDelete, id);
    }

    @Override
    public Friendship findById(Long integer) {
        throw new UnsupportedOperationException();
        // return null;
    }
}
