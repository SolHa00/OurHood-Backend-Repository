package hello.photo.domain.room.converter;

import hello.photo.domain.room.dto.request.RoomCreateRequest;
import hello.photo.domain.room.dto.response.RoomCreateResponse;
import hello.photo.domain.room.dto.response.RoomListInfo;
import hello.photo.domain.room.dto.response.RoomListResponse;
import hello.photo.domain.room.entity.Room;

import java.util.List;

public class RoomConverter {

    public static Room toRoom(RoomCreateRequest request) {
        return Room.builder()
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
}