package server.photo.domain.room.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomListDetail {
    private String roomName;
    private String thumbnail;

    @Builder
    public RoomListDetail(String roomName, String thumbnail) {
        this.roomName = roomName;
        this.thumbnail = thumbnail;
    }
}
