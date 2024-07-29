package hello.photo.domain.user.dto.response;

import hello.photo.domain.invitation.dto.InvitationInfo;
import hello.photo.domain.room.dto.RoomInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MyPageResponse {
    private MyInfo myInfo;
    private List<RoomInfo> rooms;
    private List<InvitationInfo> invitations;

    public MyPageResponse(MyInfo myInfo, List<RoomInfo> rooms, List<InvitationInfo> invitations) {
        this.myInfo = myInfo;
        this.rooms = rooms;
        this.invitations = invitations;
    }

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
