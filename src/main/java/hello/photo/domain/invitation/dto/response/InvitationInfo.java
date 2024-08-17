package hello.photo.domain.invitation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
public class InvitationInfo {
    private Long invitationId;
    private OffsetDateTime createdAt;
    private Long roomId;
    private String roomName;
    private String hostName;
}
