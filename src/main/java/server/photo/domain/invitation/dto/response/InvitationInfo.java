package server.photo.domain.invitation.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.photo.domain.user.dto.response.InvitationMetadata;
import server.photo.domain.user.dto.response.InvitingRoomInfo;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InvitationInfo {

    private InvitationMetadata invitationMetadata;
    private InvitingRoomInfo invitingRoomInfo;

    @Builder
    public InvitationInfo(InvitationMetadata invitationMetadata, InvitingRoomInfo invitingRoomInfo) {
        this.invitationMetadata = invitationMetadata;
        this.invitingRoomInfo = invitingRoomInfo;
    }
}
