package server.photo.domain.invitation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InvitationInfo {

    private Long invitationId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    private String roomName;
    private String hostName;

    @Builder
    public InvitationInfo(Long invitationId, LocalDateTime createdAt, String roomName, String hostName) {
        this.invitationId = invitationId;
        this.createdAt = createdAt;
        this.roomName = roomName;
        this.hostName = hostName;
    }
}
