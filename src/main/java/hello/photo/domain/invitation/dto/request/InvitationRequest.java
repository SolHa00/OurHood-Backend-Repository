package hello.photo.domain.invitation.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvitationRequest {
    private Long roomId;
    private String nickname;
}
