package server.photo.domain.room.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomListResponse {
    private RoomMetadata roomMetadata;
    private RoomListDetail roomDetail;

    @Builder
    public RoomListResponse(RoomMetadata roomMetadata, RoomListDetail roomDetail) {
        this.roomMetadata = roomMetadata;
        this.roomDetail = roomDetail;
    }
}
