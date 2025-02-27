package server.photo.domain.user.converter;

import server.photo.domain.invitation.dto.response.InvitationInfo;
import server.photo.domain.invitation.entity.Invitation;
import server.photo.domain.join.entity.JoinRequest;
import server.photo.domain.room.entity.Room;
import server.photo.domain.user.dto.request.UserSignUpRequest;
import server.photo.domain.user.dto.response.*;
import server.photo.domain.user.entity.User;

public class UserConverter {

    public static User toUser(UserSignUpRequest request, String password) {
        return User.builder()
                .nickname(request.getNickname())
                .email(request.getEmail())
                .password(password)
                .build();
    }

    public static UserLoginInfo toUserLoginInfo(User user) {
        return UserLoginInfo.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();
    }

    public static UserLoginResponse toUserLoginResponse(UserLoginInfo userLoginInfo) {
        return UserLoginResponse.builder()
                .user(userLoginInfo)
                .build();
    }

    public static RoomInfo toRoomInfo(RoomMetadata roomMetadata, RoomDetail roomDetail) {
        return RoomInfo.builder()
                .roomMetadata(roomMetadata)
                .roomDetail(roomDetail)
                .build();
    }

    public static RoomMetadata toRoomMetadata(Room room, User host) {
        return RoomMetadata.builder()
                .roomId(room.getId())
                .hostName(host.getNickname())
                .numOfMembers(room.getRoomMembers().size())
                .createdAt(room.getCreatedAt())
                .build();
    }

    public static RoomDetail toRoomDetail(Room room) {
        return RoomDetail.builder()
                .roomName(room.getRoomName())
                .thumbnail(room.getThumbnailImage())
                .build();
    }

    public static JoinRequestInfo toJoinRequestInfo(JoinRequest joinRequest, Room room) {
        return JoinRequestInfo.builder()
                .joinRequestId(joinRequest.getId())
                .roomName(room.getRoomName())
                .createdAt(joinRequest.getCreatedAt())
                .build();
    }

    public static InvitationInfo toInvitationInfo(Invitation invitation, Room room, User host) {
        return InvitationInfo.builder()
                .invitationId(invitation.getId())
                .createdAt(invitation.getCreatedAt())
                .roomName(room.getRoomName())
                .hostName(host.getNickname())
                .build();
    }
}
