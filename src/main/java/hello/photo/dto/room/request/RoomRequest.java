package hello.photo.dto.room.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomRequest {
    private String roomName;
    private String roomDescription;
    private Long userId;
}