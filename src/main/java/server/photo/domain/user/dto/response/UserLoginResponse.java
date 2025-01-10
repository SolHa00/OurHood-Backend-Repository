package server.photo.domain.user.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLoginResponse {

    private UserLoginInfo user;

    @Builder
    public UserLoginResponse(UserLoginInfo user) {
        this.user = user;
    }
}
