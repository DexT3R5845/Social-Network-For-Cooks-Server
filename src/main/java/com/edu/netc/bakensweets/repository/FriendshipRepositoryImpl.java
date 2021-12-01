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
    @Value("${sql.friendship.findByFriendshipAcceptedAsc}")
    private String sqlFindByFriendshipAcceptedAsc;
    @Value("${sql.friendship.findByFriendshipAcceptedDesc}")
    private String sqlFindByFriendshipAcceptedDesc;
    @Value("${sql.friendship.countByFriendshipAccepted}")
    private String sqlCountByFriendshipAccepted;
    @Value("${sql.friendship.findAllNotFriendsAsc}")
    private String sqlFindAllNotFriendsAsc;
    @Value("${sql.friendship.findAllNotFriendsDesc}")
    private String sqlFindAllNotFriendsDesc;
    @Value("${sql.friendship.countAllNotFriends}")
    private String sqlCountAllNotFriends;
    @Value("${sql.friendship.findByFriendshipUnacceptedAsc}")
    private String sqlFindByFriendshipUnacceptedAsc;
    @Value("${sql.friendship.findByFriendshipUnacceptedDesc}")
    private String sqlFindByFriendshipUnacceptedDesc;
    @Value("${sql.friendship.countByFriendshipUnaccepted}")
    private String sqlCountByFriendshipUnaccepted;

    public FriendshipRepositoryImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void updateFriendshipStatus(long id, FriendshipStatus friendshipStatus) {
        jdbcTemplate.update(sqlUpdateFriendshipStatus, friendshipStatus.toString(), id);
    }

    @Override
    public Collection<Account> findByFriendshipAccepted(long inviterId, String search, String gender, int limit,
                                                      int offset, boolean order) {
        String sqlFindByFriendshipAccepted = order ? sqlFindByFriendshipAcceptedAsc : sqlFindByFriendshipAcceptedDesc;
        return jdbcTemplate.query(sqlFindByFriendshipAccepted,
                new BeanPropertyRowMapper<>(Account.class), inviterId, inviterId, inviterId, gender,
                search, search, limit, offset);
    }

    @Override
    public Collection<Account> findByFriendshipUnaccepted(long inviterId, String search, String gender, int limit,
                                                          int offset, boolean order) {
        String sqlFindByFriendshipUnaccepted = order ? sqlFindByFriendshipUnacceptedAsc : sqlFindByFriendshipUnacceptedDesc;
        return jdbcTemplate.query(sqlFindByFriendshipUnaccepted,
                new BeanPropertyRowMapper<>(Account.class), inviterId, gender, search, search, limit, offset);
    }



    @Override
    public Collection<Account> findFriendsToAdd(long inviterId, String search, String gender, int limit, int offset,
                                                boolean order) {
        String sqlFindAllNotFriends = order ? sqlFindAllNotFriendsAsc : sqlFindAllNotFriendsDesc;
        return jdbcTemplate.query(sqlFindAllNotFriends,
                new BeanPropertyRowMapper<>(Account.class), inviterId, inviterId, inviterId, gender, search, search, limit, offset);
    }

    @Override
    public Friendship findByInviterAndFriend(long inviterId, long friendId) {
        return jdbcTemplate.queryForObject(sqlFindByInviterAndFriend,
                new BeanPropertyRowMapper<>(Friendship.class), inviterId, friendId);
    }

    @Override
    public int countByFriendshipAccepted(long inviterId, String search, String gender) {
        Integer count = jdbcTemplate.queryForObject(sqlCountByFriendshipAccepted,Integer.class, inviterId, inviterId, inviterId, gender, search, search);
        return count == null ? 0 : count;
    }

    @Override
    public int countByFriendshipUnaccepted(long inviterId, String search, String gender) {
        Integer count = jdbcTemplate.queryForObject(sqlCountByFriendshipUnaccepted,Integer.class,  inviterId, gender, search, search);
        System.out.println(count);
        return count == null ? 0 : count;
    }

    @Override
    public int countFriendsToAdd(long inviterId, String search, String gender) {
        Integer count = jdbcTemplate.queryForObject(sqlCountAllNotFriends,Integer.class, inviterId, inviterId, inviterId, gender, search, search);
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
