package hello.photo.dto.invitation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvitationRequest {
    private Long roomId;
    private String nickname;
}
