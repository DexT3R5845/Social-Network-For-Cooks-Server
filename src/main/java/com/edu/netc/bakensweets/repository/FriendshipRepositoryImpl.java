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
    private String sqlCreate;
    @Value("${sql.friendship.deleteByInviterAndFriend}")
    private String sqlDeleteByInviterAndFriend;
    @Value("${sql.friendship.findByInviterAndFriend}")
    private String sqlFindByInviterAndFriend;
    @Value("${sql.friendship.updateStatusByInviterAndFriend}")
    private String sqlUpdateStatusByInviterAndFriend;
    @Value("${sql.friendship.findByFriendshipAccepted}")
    private String sqlFindByFriendshipAccepted;
    @Value("${sql.friendship.countByFriendshipAccepted}")
    private String sqlCountByFriendshipAccepted;
    @Value("${sql.friendship.findAllNotFriends}")
    private String sqlFindAllNotFriends;
    @Value("${sql.friendship.countAllNotFriends}")
    private String sqlCountAllNotFriends;
    @Value("${sql.friendship.findByFriendshipUnaccepted}")
    private String sqlFindByFriendshipUnaccepted;
    @Value("${sql.friendship.countByFriendshipUnaccepted}")
    private String sqlCountByFriendshipUnaccepted;

    public FriendshipRepositoryImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }


    @Override
    public Collection<Account> findByFriendshipAccepted(long inviterId, String search, String gender, int limit,
                                                        int offset, boolean order) {
        String query = sqlFindByFriendshipAccepted.replace("order", order ? "ASC" : "DESC");
        return jdbcTemplate.query(query,
                new BeanPropertyRowMapper<>(Account.class), inviterId, inviterId, inviterId, gender,
                search, search, limit, offset);
    }

    @Override
    public Collection<Account> findByFriendshipUnaccepted(long friendId, String search, String gender, int limit,
                                                          int offset, boolean order) {
        String query = sqlFindByFriendshipUnaccepted.replace("order", order ? "ASC" : "DESC");
        return jdbcTemplate.query(query,
                new BeanPropertyRowMapper<>(Account.class), friendId, gender, search, search, limit, offset);
    }


    @Override
    public Collection<Account> findFriendsToAdd(long accountId, String search, String gender, int limit, int offset,
                                                boolean order) {
        String query = sqlFindAllNotFriends.replace("order", order ? "ASC" : "DESC");
        return jdbcTemplate.query(query,
                new BeanPropertyRowMapper<>(Account.class), accountId, accountId, accountId, gender, search, search, limit, offset);
    }

    @Override
    public boolean findByInviterAndFriend(long inviterId, long friendId) {
        Integer count  = jdbcTemplate.queryForObject(sqlFindByInviterAndFriend, Integer.class, inviterId, friendId, inviterId, friendId);
        return count == 0;
    }

    @Override
    public boolean deleteByInviterAndFriend(long inviterId, long friendId) {
        return jdbcTemplate.update(sqlDeleteByInviterAndFriend, inviterId, friendId, friendId, inviterId) != 0;
    }

    @Override
    public boolean updateStatusByInviterAndFriend(FriendshipStatus friendshipStatus, long inviterId, long friendId) {
        return jdbcTemplate.update(sqlUpdateStatusByInviterAndFriend, friendshipStatus.toString(), inviterId, friendId) != 0;
    }

    @Override
    public int countByFriendshipAccepted(long inviterId, String search, String gender) {
        Integer count = jdbcTemplate.queryForObject(sqlCountByFriendshipAccepted, Integer.class, inviterId, inviterId, inviterId, gender, search, search);
        return count == null ? 0 : count;
    }

    @Override
    public int countByFriendshipUnaccepted(long inviterId, String search, String gender) {
        Integer count = jdbcTemplate.queryForObject(sqlCountByFriendshipUnaccepted, Integer.class, inviterId, gender, search, search);
        return count == null ? 0 : count;
    }

    @Override
    public int countFriendsToAdd(long accountId, String search, String gender) {
        Integer count = jdbcTemplate.queryForObject(sqlCountAllNotFriends, Integer.class, accountId, accountId, accountId, gender, search, search);
        return count == null ? 0 : count;
    }

    @Override
    public long create(Friendship friendship) {
        jdbcTemplate.update(sqlCreate, friendship.getInviterId(), friendship.getFriendId());
        return 0;
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
