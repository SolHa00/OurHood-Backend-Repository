package hello.photo.domain.room.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomCreateResponse {
    private Long roomId;

    public RoomCreateResponse(Long roomId) {
        this.roomId = roomId;
    }
}
