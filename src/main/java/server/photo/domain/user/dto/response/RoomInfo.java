package server.photo.domain.user.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomInfo {

    private RoomMetadata roomMetadata;
    private RoomDetail roomDetail;

    @Builder
    public RoomInfo(RoomMetadata roomMetadata, RoomDetail roomDetail) {
        this.roomMetadata = roomMetadata;
        this.roomDetail = roomDetail;
    }
}
