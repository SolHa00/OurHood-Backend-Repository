package server.photo.domain.room.converter;

import server.photo.domain.invitation.dto.response.InvitationInfo;
import server.photo.domain.invitation.entity.Invitation;
import server.photo.domain.room.dto.request.RoomCreateRequest;
import server.photo.domain.room.dto.response.*;
import server.photo.domain.room.entity.Room;
import server.photo.domain.user.dto.response.MyInfo;
import server.photo.domain.user.dto.response.MyPageResponse;
import server.photo.domain.user.dto.response.RoomsMyPageInfo;
import server.photo.domain.user.entity.User;

import java.util.List;

public class RoomConverter {

    public static Room toRoom(RoomCreateRequest request, User user) {
        return Room.builder()
                .userId(user.getId())
                .roomName(request.getRoomName())
                .roomDescription(request.getRoomDescription())
                .build();
    }

    public static RoomCreateResponse toRoomCreateResponse(Room room) {
        return RoomCreateResponse.builder()
                .roomId(room.getId())
                .build();
    }


    public static RoomListInfo toRoomListInfo(Room room, String hostName) {
        return RoomListInfo.builder()
                .roomId(room.getId())
                .roomName(room.getRoomName())
                .hostName(hostName)
                .numOfMembers(room.getRoomMembers().size())
                .createdAt(room.getCreatedAt())
                .thumbnail(room.getThumbnailImage())
                .build();
    }

    public static RoomListResponse toRoomListResponse(List<RoomListInfo> roomListInfos) {
        return RoomListResponse.builder()
                .rooms(roomListInfos)
                .build();
    }

    public static MyPageResponse toMyPageResponse(MyInfo myInfo, List<RoomsMyPageInfo> hostedRooms, List<InvitationInfo> invitations) {
        return MyPageResponse.builder()
                .myInfo(myInfo)
                .rooms(hostedRooms)
                .invitations(invitations)
                .build();
    }

    public static RoomInvitationList toRoomInvitationList(Invitation invitation, User inviter) {
        return RoomInvitationList.builder()
                .invitationId(invitation.getId())
                .nickname(inviter.getNickname())
                .createdAt(invitation.getCreatedAt())
                .build();
    }

    public static RoomInvitationsDto toRoomInvitationsDto(List<RoomInvitationList> invitationLists) {
        return RoomInvitationsDto.builder()
                .invitaionList(invitationLists)
                .build();
    }
}