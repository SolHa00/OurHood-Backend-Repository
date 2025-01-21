package server.photo.domain.user.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomDetail {

    private String roomName;
    private String thumbnail;

    @Builder
    public RoomDetail(String roomName, String thumbnail) {
        this.roomName = roomName;
        this.thumbnail = thumbnail;
    }
}
