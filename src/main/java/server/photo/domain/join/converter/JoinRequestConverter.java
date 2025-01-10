package server.photo.domain.join.converter;

import server.photo.domain.join.entity.JoinRequest;
import server.photo.domain.room.entity.Room;
import server.photo.domain.user.entity.User;

public class JoinRequestConverter {

    public static JoinRequest toJoinRequest(Room room, User user) {
        return JoinRequest.builder()
                .room(room)
                .userId(user.getId())
                .build();
    }
}
