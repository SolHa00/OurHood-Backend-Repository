package hello.photo.dto.user.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignupResponse {
    private Long userId;
    private String token;

    public UserSignupResponse(Long userId, String token) {
        this.userId = userId;
        this.token = token;
    }
}