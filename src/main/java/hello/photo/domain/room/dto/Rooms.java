package hello.photo.domain.room.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class Rooms {
    private Long roomId;
    private String roomName;
    private String hostName;
    private int numOfMembers;
    private OffsetDateTime createdAt;

    public Rooms(Long roomId, String roomName, String hostName, int numOfMembers, OffsetDateTime createdAt) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.hostName = hostName;
        this.numOfMembers = numOfMembers;
        this.createdAt = createdAt;
    }
}
