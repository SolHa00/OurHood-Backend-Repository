package hello.photo.dto.room.response;

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

    public RoomDetailResponse(boolean isMember, Long roomId, String roomName, String hostName, String roomDescription) {
        this.isMember = isMember;
        this.roomId = roomId;
        this.roomName = roomName;
        this.hostName = hostName;
        this.roomDescription = roomDescription;
    }

}
