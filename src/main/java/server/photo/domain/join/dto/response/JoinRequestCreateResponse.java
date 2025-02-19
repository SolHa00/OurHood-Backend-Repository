package server.photo.domain.join.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JoinRequestCreateResponse {
    private Long joinRequestId;

    @Builder
    public JoinRequestCreateResponse(Long joinRequestId) {
        this.joinRequestId = joinRequestId;
    }
}
