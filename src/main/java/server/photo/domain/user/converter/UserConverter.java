package server.photo.domain.user.converter;

import server.photo.domain.invitation.dto.response.InvitationInfo;
import server.photo.domain.invitation.entity.Invitation;
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

    public static MyInfo toMyInfo(User user) {
        return MyInfo.builder()
                .nickname(user.getNickname())
                .email(user.getEmail())
                .build();
    }

    public static InvitationInfo toInvitationInfo(InvitationMetaData invitationMetaData, InvitingRoomInfo invitingRoomInfo) {
        return InvitationInfo.builder()
                .invitationMetaData(invitationMetaData)
                .invitingRoomInfo(invitingRoomInfo)
                .build();
    }

    public static RoomInfo toRoomInfo(RoomMetaData roomMetaData, RoomDetail roomDetail) {
        return RoomInfo.builder()
                .roomMetaData(roomMetaData)
                .roomDetail(roomDetail)
                .build();
    }

    public static InvitationMetaData toInvitationMetaData(Invitation invitation) {
        return InvitationMetaData.builder()
                .invitationId(invitation.getId())
                .createdAt(invitation.getCreatedAt())
                .build();
    }

    public static InvitingRoomInfo toInvitingRoomInfo(Room room, User host) {
        return InvitingRoomInfo.builder()
                .roomId(room.getId())
                .roomName(room.getRoomName())
                .hostName(host.getNickname())
                .build();
    }

    public static RoomMetaData toRoomMetaData(Room room, User host) {
        return RoomMetaData.builder()
                .roomId(room.getId())
                .hostName(host.getNickname())
                .numOfMembers(room.getRoomMembers().size())
                .createdAt(room.getCreatedAt())
                .build();
    }

    public static RoomDetail toRoomDetail(Room room) {
        return RoomDetail.builder()
                .roomName(room.getRoomName())
                .build();
    }
}
