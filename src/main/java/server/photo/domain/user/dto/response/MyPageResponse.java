package server.photo.domain.user.dto.response;

import server.photo.domain.invitation.dto.response.InvitationInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyPageResponse {

    private List<RoomInfo> myRooms;
    private List<InvitationInfo> invitations;

    @Builder
    public MyPageResponse(List<RoomInfo> myRooms, List<InvitationInfo> invitations) {
        this.myRooms = myRooms;
        this.invitations = invitations;
    }
}
