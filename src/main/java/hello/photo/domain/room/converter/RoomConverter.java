package hello.photo.domain.room.converter;

import hello.photo.domain.invitation.dto.response.InvitationInfo;
import hello.photo.domain.room.dto.request.RoomCreateRequest;
import hello.photo.domain.room.dto.response.RoomCreateResponse;
import hello.photo.domain.room.dto.response.RoomListInfo;
import hello.photo.domain.room.dto.response.RoomListResponse;
import hello.photo.domain.room.entity.Room;
import hello.photo.domain.user.dto.response.MyInfo;
import hello.photo.domain.user.dto.response.MyPageResponse;
import hello.photo.domain.user.dto.response.RoomsMyPageInfo;
import hello.photo.domain.user.entity.User;

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
}