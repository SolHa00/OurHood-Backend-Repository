package hello.photo.dto.user.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignupRequest {
    private String nickname;
    private String email;
    private String password;
}
