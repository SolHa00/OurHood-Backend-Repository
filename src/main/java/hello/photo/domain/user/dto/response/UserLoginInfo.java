package hello.photo.domain.user.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginInfo {
    private Long userId;
    private String email;
    private String nickname;
}
