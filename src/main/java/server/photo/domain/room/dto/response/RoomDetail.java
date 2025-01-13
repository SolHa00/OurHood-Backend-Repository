package server.photo.domain.room.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomDetail {
    private String roomName;
    private String roomDescription;
    private String thumbnail;

    @Builder
    public RoomDetail(String roomName, String roomDescription, String thumbnail) {
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.thumbnail = thumbnail;
    }
}
