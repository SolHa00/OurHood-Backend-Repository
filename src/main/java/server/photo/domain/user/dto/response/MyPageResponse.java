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
    private List<InvitationInfo> receivedInvitations;
    private List<JoinRequestInfo> sentJoinRequests;

    @Builder
    public MyPageResponse(List<RoomInfo> myRooms, List<InvitationInfo> receivedInvitations, List<JoinRequestInfo> sentJoinRequests) {
        this.myRooms = myRooms;
        this.receivedInvitations = receivedInvitations;
        this.sentJoinRequests = sentJoinRequests;
    }
}
