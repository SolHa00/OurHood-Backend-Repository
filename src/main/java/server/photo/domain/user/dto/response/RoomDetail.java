package server.photo.domain.user.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomDetail {

    private String roomName;

    @Builder
    public RoomDetail(String roomName) {
        this.roomName = roomName;
    }
}
