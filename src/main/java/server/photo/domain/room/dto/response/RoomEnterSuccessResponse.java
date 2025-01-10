package server.photo.domain.room.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomEnterSuccessResponse {

    private Boolean isMember;
    private Long roomId;
    private String roomName;
    private String roomDescription;
    private String hostName;
    private RoomEnterInfo roomDetail;
    private String thumbnail;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    private Long userId;

    @Builder
    public RoomEnterSuccessResponse(Boolean isMember, Long roomId, String roomName, String roomDescription, String hostName, RoomEnterInfo roomDetail, String thumbnail, LocalDateTime createdAt, Long userId) {
        this.isMember = isMember;
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.hostName = hostName;
        this.roomDetail = roomDetail;
        this.thumbnail = thumbnail;
        this.createdAt = createdAt;
        this.userId = userId;
    }
}
