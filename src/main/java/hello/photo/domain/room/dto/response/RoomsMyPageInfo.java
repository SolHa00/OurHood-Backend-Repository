package hello.photo.domain.room.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomsMyPageInfo {
    private Long roomId;
    private String roomName;
    private String hostName;
    private int numOfMembers;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private OffsetDateTime createdAt;

    @Builder
    public RoomsMyPageInfo(Long roomId, String roomName, String hostName, int numOfMembers, OffsetDateTime createdAt) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.hostName = hostName;
        this.numOfMembers = numOfMembers;
        this.createdAt = createdAt;
    }
}
