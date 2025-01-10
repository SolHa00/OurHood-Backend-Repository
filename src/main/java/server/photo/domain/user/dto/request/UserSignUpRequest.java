package server.photo.domain.user.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpRequest {
    private String nickname;
    private String email;
    private String password;
}
