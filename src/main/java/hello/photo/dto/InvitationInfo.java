package hello.photo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class InvitationInfo {
    private Long invitationId;
    private LocalDateTime createdAt;
    private Long roomId;
    private String roomName;
    private String hostName;

    public InvitationInfo(Long invitationId, LocalDateTime createdAt, Long roomId, String roomName, String hostName) {
        this.invitationId = invitationId;
        this.createdAt = createdAt;
        this.roomId = roomId;
        this.roomName = roomName;
        this.hostName = hostName;
    }
}
