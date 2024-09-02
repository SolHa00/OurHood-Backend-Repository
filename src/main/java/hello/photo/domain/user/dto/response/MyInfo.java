package hello.photo.domain.user.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyInfo {
    private String nickname;
    private String email;

    @Builder
    public MyInfo(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }
}
