package hello.photo.domain.user.dto.response;

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