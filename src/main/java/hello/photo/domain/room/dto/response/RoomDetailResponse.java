package hello.photo.domain.room.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomDetailResponse {
    private boolean isMember;
    private Long roomId;
    private String roomName;
    private String roomDescription;
    private String hostName;

    public RoomDetailResponse(boolean isMember, Long roomId, String roomName, String roomDescription, String hostName) {
        this.isMember = isMember;
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.hostName = hostName;
    }

}
