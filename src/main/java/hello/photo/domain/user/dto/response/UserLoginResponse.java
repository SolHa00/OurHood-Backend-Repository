package hello.photo.domain.user.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginResponse {
    private Long userId;
    private String nickname;

    public UserLoginResponse(Long userId, String nickname) {
        this.userId = userId;
        this.nickname = nickname;
    }
}
