package hello.photo.domain.user.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginResponse {
    private Long userId;
    private String nickname;
    private String token;

    public UserLoginResponse(Long userId, String nickname, String token) {
        this.userId = userId;
        this.nickname = nickname;
        this.token = token;
    }
}
