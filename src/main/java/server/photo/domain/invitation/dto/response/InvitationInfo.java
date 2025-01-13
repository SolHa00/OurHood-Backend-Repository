package server.photo.domain.invitation.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.photo.domain.user.dto.response.InvitationMetaData;
import server.photo.domain.user.dto.response.InvitingRoomInfo;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InvitationInfo {

    private InvitationMetaData invitationMetaData;
    private InvitingRoomInfo invitingRoomInfo;

    @Builder
    public InvitationInfo(InvitationMetaData invitationMetaData, InvitingRoomInfo invitingRoomInfo) {
        this.invitationMetaData = invitationMetaData;
        this.invitingRoomInfo = invitingRoomInfo;
    }
}
