package com.edu.netc.bakensweets.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Friendship {
    @Id
    private long id;
    private long inviterId;
    private long friendId;
    private FriendshipStatus friendshipStatus;

    public Friendship(long inviterId, long friendId) {
        this.inviterId = inviterId;
        this.friendId = friendId;
    }
}

