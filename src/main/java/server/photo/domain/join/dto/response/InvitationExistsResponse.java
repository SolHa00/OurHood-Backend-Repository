package server.photo.domain.join.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InvitationExistsResponse {
    private Long invitationId;

    @Builder
    public InvitationExistsResponse(Long invitationId) {
        this.invitationId = invitationId;
    }
}
