package hello.photo.domain.join.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JoinRequestDetail {
    private Long joinId;
    private String nickName;

    @Builder
    public JoinRequestDetail(Long joinId, String nickName) {
        this.joinId = joinId;
        this.nickName = nickName;
    }
}
