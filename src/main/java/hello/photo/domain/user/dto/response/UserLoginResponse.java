package hello.photo.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserLoginResponse {
    private Long userId;
    private String email;
    private String nickname;
}
