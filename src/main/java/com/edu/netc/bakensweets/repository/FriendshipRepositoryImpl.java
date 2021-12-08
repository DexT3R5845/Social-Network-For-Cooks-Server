package com.edu.netc.bakensweets.repository;

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
    @Value("${sql.friendship.delete}")
    private String sqlDelete;
    @Value("${sql.friendship.findByInviterAndFriend}")
    private String sqlFindByInviterAndFriend;
    @Value("${sql.friendship.updateFriendshipStatus}")
    private String sqlUpdateFriendshipStatus;


    public FriendshipRepositoryImpl(JdbcTemplate jdbcTemplate) {super(jdbcTemplate);}

    @Override
    public void updateFriendshipStatus(long id, FriendshipStatus friendshipStatus) {
        jdbcTemplate.update(sqlUpdateFriendshipStatus, friendshipStatus.toString(), id);
    }

    @Override
    public Collection<Friendship> findByFriendshipStatus(FriendshipStatus friendshipStatus) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Friendship findByInviterAndFriend(long inviterId, long friendId) {
        return jdbcTemplate.queryForObject(sqlFindByInviterAndFriend,
                new BeanPropertyRowMapper<>(Friendship.class), inviterId, friendId);
    }

    @Override
    public void create(Friendship friendship) {
        jdbcTemplate.update(sqlCreate, friendship.getInviterId(), friendship.getFriendId());
    }

    @Override
    public boolean update(Friendship item) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean deleteById(Long id) {
        jdbcTemplate.update(sqlDelete, id);
        return true;
    }

    @Override
    public Friendship findById(Long integer) {
        throw new UnsupportedOperationException();
       // return null;
    }
}
