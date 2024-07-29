package hello.photo.domain.room.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomResponse {
    private Long roomId;

    public RoomResponse(Long roomId) {
        this.roomId = roomId;
    }
}
