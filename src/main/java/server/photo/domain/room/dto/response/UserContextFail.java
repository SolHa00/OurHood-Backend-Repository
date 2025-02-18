package server.photo.domain.room.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserContextFail {
    private Boolean isMember;
    private Long sentJoinRequestId;
    private Boolean isHost;

    @Builder
    public UserContextFail(Boolean isMember, Long sentJoinRequestId, Boolean isHost) {
        this.isMember = isMember;
        this.sentJoinRequestId = sentJoinRequestId;
        this.isHost = isHost;
    }
}
