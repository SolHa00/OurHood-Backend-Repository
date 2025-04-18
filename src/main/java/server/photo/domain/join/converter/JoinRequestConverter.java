package server.photo.domain.join.converter;

import server.photo.domain.join.dto.response.InvitationExistsResponse;
import server.photo.domain.join.dto.response.JoinRequestCreateResponse;
import server.photo.domain.join.entity.JoinRequest;
import server.photo.domain.room.entity.Room;
import server.photo.domain.user.entity.User;

public class JoinRequestConverter {

    public static JoinRequest toJoinRequest(Room room, User user) {
        return JoinRequest.builder()
                .room(room)
                .user(user)
                .build();
    }

    public static JoinRequestCreateResponse toJoinRequestCreateResponse(JoinRequest joinRequest) {
        return JoinRequestCreateResponse.builder()
                .joinRequestId(joinRequest.getId())
                .build();
    }

    public static InvitationExistsResponse toInvitationExistsResponse(Long invitationId) {
        return InvitationExistsResponse.builder()
                .invitationId(invitationId)
                .build();
    }
}
