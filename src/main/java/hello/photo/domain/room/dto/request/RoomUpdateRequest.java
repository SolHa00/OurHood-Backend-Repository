package hello.photo.domain.room.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomUpdateRequest {
    private String roomName;
    private String roomDescription;
}
