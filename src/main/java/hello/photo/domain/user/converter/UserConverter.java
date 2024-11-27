package hello.photo.domain.user.converter;

import hello.photo.domain.invitation.dto.response.InvitationInfo;
import hello.photo.domain.invitation.entity.Invitation;
import hello.photo.domain.room.entity.Room;
import hello.photo.domain.user.dto.request.UserSignUpRequest;
import hello.photo.domain.user.dto.response.MyInfo;
import hello.photo.domain.user.dto.response.RoomsMyPageInfo;
import hello.photo.domain.user.dto.response.UserLoginInfo;
import hello.photo.domain.user.dto.response.UserLoginResponse;
import hello.photo.domain.user.entity.User;

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

    public static InvitationInfo toInvitaionInfo(Invitation invitation, User host) {
        return InvitationInfo.builder()
                .invitationId(invitation.getId())
                .createdAt(invitation.getCreatedAt())
                .roomId(invitation.getRoom().getId())
                .roomName(invitation.getRoom().getRoomName())
                .hostName(host.getNickname())
                .build();
    }

    public static RoomsMyPageInfo toRoomsMyPageInfo(Room room, User host) {
        return RoomsMyPageInfo.builder()
                .roomId(room.getId())
                .roomName(room.getRoomName())
                .hostName(host.getNickname())
                .numOfMembers(room.getRoomMembers().size())
                .createdAt(room.getCreatedAt())
                .thumbnail(room.getThumbnailImage())
                .build();
    }
}
