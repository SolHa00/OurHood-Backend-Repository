package hello.photo.domain.invitation.converter;

import hello.photo.domain.invitation.entity.Invitation;
import hello.photo.domain.room.entity.Room;
import hello.photo.domain.user.entity.User;

public class InvitationConverter {

    public static Invitation toInvitation(Room room, User user) {
        return Invitation.builder()
                .room(room)
                .userId(user.getId())
                .build();
    }
}
