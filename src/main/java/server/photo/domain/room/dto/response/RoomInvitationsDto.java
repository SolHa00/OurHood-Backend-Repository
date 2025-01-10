package server.photo.domain.room.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomInvitationsDto {
    private List<RoomInvitationList> invitaionList;

    @Builder
    public RoomInvitationsDto(List<RoomInvitationList> invitaionList) {
        this.invitaionList = invitaionList;
    }
}
