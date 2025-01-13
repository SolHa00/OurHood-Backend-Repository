package server.photo.domain.user.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InvitingRoomInfo {

    private Long roomId;
    private String roomName;
    private String hostName;

    @Builder
    public InvitingRoomInfo(Long roomId, String roomName, String hostName) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.hostName = hostName;
    }
}
