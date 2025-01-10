package server.photo.domain.room.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JoinRequestDetail {
    private Long joinId;
    private String nickname;

    @Builder
    public JoinRequestDetail(Long joinId, String nickname) {
        this.joinId = joinId;
        this.nickname = nickname;
    }
}
