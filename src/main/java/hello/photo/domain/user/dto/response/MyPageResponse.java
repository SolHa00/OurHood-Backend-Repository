package hello.photo.domain.user.dto.response;

import hello.photo.domain.invitation.dto.response.InvitationInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyPageResponse {

    private MyInfo myInfo;
    private List<RoomsMyPageInfo> rooms;
    private List<InvitationInfo> invitations;

    @Builder
    public MyPageResponse(MyInfo myInfo, List<RoomsMyPageInfo> rooms, List<InvitationInfo> invitations) {
        this.myInfo = myInfo;
        this.rooms = rooms;
        this.invitations = invitations;
    }
}
