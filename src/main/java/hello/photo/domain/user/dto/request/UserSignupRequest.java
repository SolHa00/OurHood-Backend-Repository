package hello.photo.domain.user.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignupRequest {
    private String nickname;
    private String email;
    private String password;
}
