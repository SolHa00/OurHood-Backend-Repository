package server.photo.domain.room.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomEnterSuccessResponse {

    private UserContextSuccess userContext;
    private RoomMetadataSuccess roomMetadata;
    private RoomDetail roomDetail;
    private RoomPrivate roomPrivate;

    @Builder
    public RoomEnterSuccessResponse(UserContextSuccess userContext, RoomMetadataSuccess roomMetadata, RoomDetail roomDetail, RoomPrivate roomPrivate) {
        this.userContext = userContext;
        this.roomMetadata = roomMetadata;
        this.roomDetail = roomDetail;
        this.roomPrivate = roomPrivate;
    }
}
