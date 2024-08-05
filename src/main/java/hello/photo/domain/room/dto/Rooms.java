package hello.photo.domain.room.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Rooms {
    private Long roomId;
    private String roomName;
    private String hostName;
    private int numOfMembers;
    private LocalDateTime createdAt;

    public Rooms(Long roomId, String roomName, String hostName, int numOfMembers, LocalDateTime createdAt) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.hostName = hostName;
        this.numOfMembers = numOfMembers;
        this.createdAt = createdAt;
    }
}
