package server.photo.domain.user.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomInfo {

    private RoomMetaData roomMetaData;
    private RoomDetail roomDetail;

    @Builder
    public RoomInfo(RoomMetaData roomMetaData, RoomDetail roomDetail) {
        this.roomMetaData = roomMetaData;
        this.roomDetail = roomDetail;
    }
}
