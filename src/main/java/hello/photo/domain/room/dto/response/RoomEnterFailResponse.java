package hello.photo.domain.room.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomEnterFailResponse {

    private Boolean isMember;
    private Long roomId;
    private String roomName;
    private String roomDescription;
    private String hostName;
    private String thumbnail;
    private Boolean isJoinRequestSent;

    @Builder
    public RoomEnterFailResponse(Boolean isMember, Long roomId, String roomName, String roomDescription, String hostName, String thumbnail, Boolean isJoinRequestSent) {
        this.isMember = isMember;
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.hostName = hostName;
        this.thumbnail = thumbnail;
        this.isJoinRequestSent = isJoinRequestSent;
    }
}
