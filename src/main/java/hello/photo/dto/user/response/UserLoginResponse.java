package hello.photo.dto.user.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginResponse {
    private Long userId;
    private String token;

    public UserLoginResponse(Long userId, String token) {
        this.userId = userId;
        this.token = token;
    }
}
