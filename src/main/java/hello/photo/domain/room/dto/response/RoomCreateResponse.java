package hello.photo.domain.room.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomCreateResponse {
    private Long roomId;

    @Builder
    public RoomCreateResponse(Long roomId) {
        this.roomId = roomId;
    }
}
