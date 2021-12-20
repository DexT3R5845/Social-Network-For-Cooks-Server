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
public class FriendshipRepositoryImpl extends BaseJdbcRepository implements FriendshipRepository {
    @Value("${sql.friendship.create}")
    private String create;
    @Value("${sql.friendship.deleteByInviterAndFriend}")
    private String deleteByInviterAndFriend;
    @Value("${sql.friendship.findByInviterAndFriend}")
    private String findByInviterAndFriend;
    @Value("${sql.friendship.updateStatusByInviterAndFriend}")
    private String updateStatusByInviterAndFriend;
    @Value("${sql.friendship.findByFriendshipAccepted}")
    private String findByFriendshipAccepted;
    @Value("${sql.friendship.countByFriendshipAccepted}")
    private String countByFriendshipAccepted;
    @Value("${sql.friendship.findAllNotFriends}")
    private String findAllNotFriends;
    @Value("${sql.friendship.countAllNotFriends}")
    private String countAllNotFriends;
    @Value("${sql.friendship.findByFriendshipUnaccepted}")
    private String findByFriendshipUnaccepted;
    @Value("${sql.friendship.countByFriendshipUnaccepted}")
    private String countByFriendshipUnaccepted;

    public FriendshipRepositoryImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }


    @Override
    public Collection<Account> findByFriendshipAccepted(long inviterId, String search, String gender, int limit,
                                                        int offset, boolean order) {
        String query = findByFriendshipAccepted.replace("order", order ? "ASC" : "DESC");
        return jdbcTemplate.query(query,
                new BeanPropertyRowMapper<>(Account.class), inviterId, inviterId, inviterId, gender,
                search, search, limit, offset);
    }

    @Override
    public Collection<Account> findByFriendshipUnaccepted(long friendId, String search, String gender, int limit,
                                                          int offset, boolean order) {
        String query = findByFriendshipUnaccepted.replace("order", order ? "ASC" : "DESC");
        return jdbcTemplate.query(query,
                new BeanPropertyRowMapper<>(Account.class), friendId, gender, search, search, limit, offset);
    }


    @Override
    public Collection<Account> findFriendsToAdd(long accountId, String search, String gender, int limit, int offset,
                                                boolean order) {
        String query = findAllNotFriends.replace("order", order ? "ASC" : "DESC");
        return jdbcTemplate.query(query,
                new BeanPropertyRowMapper<>(Account.class), accountId, accountId, accountId, gender, search, search, limit, offset);
    }

    @Override
    public boolean findByInviterAndFriend(long inviterId, long friendId) {
        Integer count  = jdbcTemplate.queryForObject(findByInviterAndFriend, Integer.class, inviterId, friendId, inviterId, friendId);
        return count == 0;
    }

    @Override
    public boolean deleteByInviterAndFriend(long inviterId, long friendId) {
        return jdbcTemplate.update(deleteByInviterAndFriend, inviterId, friendId, friendId, inviterId) != 0;
    }

    @Override
    public boolean updateStatusByInviterAndFriend(FriendshipStatus friendshipStatus, long inviterId, long friendId) {
        return jdbcTemplate.update(updateStatusByInviterAndFriend, friendshipStatus.toString(), inviterId, friendId) != 0;
    }

    @Override
    public int countByFriendshipAccepted(long inviterId, String search, String gender) {
        Integer count = jdbcTemplate.queryForObject(countByFriendshipAccepted, Integer.class, inviterId, inviterId, inviterId, gender, search, search);
        return count == null ? 0 : count;
    }

    @Override
    public int countByFriendshipUnaccepted(long inviterId, String search, String gender) {
        Integer count = jdbcTemplate.queryForObject(countByFriendshipUnaccepted, Integer.class, inviterId, gender, search, search);
        return count == null ? 0 : count;
    }

    @Override
    public int countFriendsToAdd(long accountId, String search, String gender) {
        Integer count = jdbcTemplate.queryForObject(countAllNotFriends, Integer.class, accountId, accountId, accountId, gender, search, search);
        return count == null ? 0 : count;
    }

    @Override
    public long create(Friendship friendship) {
        return jdbcTemplate.queryForObject(create, Long.class, friendship.getInviterId(), friendship.getFriendId());
    }

    @Override
    public boolean update(Friendship friendship) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean deleteById(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Friendship findById(Long integer) {
        throw new UnsupportedOperationException();
    }
}
