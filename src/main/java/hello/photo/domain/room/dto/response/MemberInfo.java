package hello.photo.domain.room.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberInfo {
    private Long userId;
    private String nickname;

    @Builder
    public MemberInfo(Long userId, String nickname) {
        this.userId = userId;
        this.nickname = nickname;
    }
}
