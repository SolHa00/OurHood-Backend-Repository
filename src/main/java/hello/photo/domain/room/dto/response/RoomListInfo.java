package hello.photo.domain.room.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomListInfo {
    private Long roomId;
    private String roomName;
    private String hostName;
    private int numOfMembers;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    private String thumbnail;

    @Builder
    public RoomListInfo(Long roomId, String roomName, String hostName, int numOfMembers, LocalDateTime createdAt, String thumbnail) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.hostName = hostName;
        this.numOfMembers = numOfMembers;
        this.createdAt = createdAt;
        this.thumbnail = thumbnail;
    }
}
