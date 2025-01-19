package server.photo.domain.room.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserContextSuccess {
    private Boolean isMember;
    private Boolean isHost;

    @Builder
    public UserContextSuccess(Boolean isMember, Boolean isHost) {
        this.isMember = isMember;
        this.isHost = isHost;
    }
}
