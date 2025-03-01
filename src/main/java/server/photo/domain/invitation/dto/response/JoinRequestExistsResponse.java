package server.photo.domain.invitation.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JoinRequestExistsResponse {
    private Long joinRequestId;

    @Builder
    public JoinRequestExistsResponse(Long joinRequestId) {
        this.joinRequestId = joinRequestId;
    }
}
