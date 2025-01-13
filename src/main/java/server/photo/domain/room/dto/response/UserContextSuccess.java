package server.photo.domain.room.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserContextSuccess {
    private Boolean isMember;

    @Builder
    public UserContextSuccess(Boolean isMember) {
        this.isMember = isMember;
    }
}
