package server.photo.domain.room.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserContextFail {
    private Boolean isMember;
    private Boolean isJoinRequestSent;

    @Builder
    public UserContextFail(Boolean isMember, Boolean isJoinRequestSent) {
        this.isMember = isMember;
        this.isJoinRequestSent = isJoinRequestSent;
    }
}
