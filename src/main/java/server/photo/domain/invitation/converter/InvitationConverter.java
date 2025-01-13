package server.photo.domain.invitation.converter;

import server.photo.domain.invitation.entity.Invitation;
import server.photo.domain.room.entity.Room;
import server.photo.domain.user.entity.User;

public class InvitationConverter {

    public static Invitation toInvitation(Room room, User user) {
        return Invitation.builder()
                .room(room)
                .userId(user.getId())
                .build();
    }
}