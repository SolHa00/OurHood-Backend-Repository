package server.photo.domain.room.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomListResponse {
    private List<RoomListInfo> rooms;

    @Builder
    public RoomListResponse(List<RoomListInfo> rooms) {
        this.rooms = rooms;
    }
}
