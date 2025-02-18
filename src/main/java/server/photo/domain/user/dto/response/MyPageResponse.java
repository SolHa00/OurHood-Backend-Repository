package server.photo.domain.user.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.photo.domain.invitation.dto.response.InvitationInfo;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyPageResponse {

    private List<RoomInfo> myRooms;
    private List<InvitationInfo> invitations;
    private List<JoinRequestInfo> myJoinRequests;

    @Builder
    public MyPageResponse(List<RoomInfo> myRooms, List<InvitationInfo> invitations, List<JoinRequestInfo> myJoinRequests) {
        this.myRooms = myRooms;
        this.invitations = invitations;
        this.myJoinRequests = myJoinRequests;
    }
}
