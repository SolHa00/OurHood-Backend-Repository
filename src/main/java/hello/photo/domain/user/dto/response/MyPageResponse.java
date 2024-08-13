package hello.photo.domain.user.dto.response;

import hello.photo.domain.invitation.dto.response.InvitationInfo;
import hello.photo.domain.room.dto.response.RoomsMyPageInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MyPageResponse {
    private MyInfo myInfo;
    private List<RoomsMyPageInfo> rooms;
    private List<InvitationInfo> invitations;

    @Getter
    @Setter
    public static class MyInfo {
        private String nickname;
        private String email;

        public MyInfo(String nickname, String email) {
            this.nickname = nickname;
            this.email = email;
        }
    }
}
