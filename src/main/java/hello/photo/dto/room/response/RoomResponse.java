package hello.photo.dto.room.response;

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
