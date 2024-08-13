package hello.photo.domain.invitation.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class InvitationInfo {
    private Long invitationId;
    private OffsetDateTime createdAt;
    private Long roomId;
    private String roomName;
    private String hostName;

    public InvitationInfo(Long invitationId, OffsetDateTime createdAt, Long roomId, String roomName, String hostName) {
        this.invitationId = invitationId;
        this.createdAt = createdAt;
        this.roomId = roomId;
        this.roomName = roomName;
        this.hostName = hostName;
    }
}
