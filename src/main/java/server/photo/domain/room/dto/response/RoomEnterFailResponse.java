package server.photo.domain.room.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomEnterFailResponse {

    private UserContextFail userContext;
    private RoomMetadataFail roomMetadata;
    private RoomDetail roomDetail;

    @Builder
    public RoomEnterFailResponse(UserContextFail userContext, RoomMetadataFail roomMetadata, RoomDetail roomDetail) {
        this.userContext = userContext;
        this.roomMetadata = roomMetadata;
        this.roomDetail = roomDetail;
    }
}
