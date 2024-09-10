package hello.photo.domain.user.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLoginResponse {

    private UserLoginInfo user;

    public UserLoginResponse(UserLoginInfo user) {
        this.user = user;
    }
}
