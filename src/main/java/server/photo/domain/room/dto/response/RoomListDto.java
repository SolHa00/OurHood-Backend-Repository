package server.photo.domain.room.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomListDto {
    private List<RoomListResponse> roomList;

    @Builder
    public RoomListDto(List<RoomListResponse> roomList) {
        this.roomList = roomList;
    }
}
