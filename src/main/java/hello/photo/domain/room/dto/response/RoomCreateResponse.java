package hello.photo.domain.room.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomCreateResponse {
    private Long roomId;
    private String thumbnail;

    @Builder
    public RoomCreateResponse(Long roomId, String thumbnail) {
        this.roomId = roomId;
        this.thumbnail = thumbnail;
    }
}
