package server.photo.domain.room.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomMetadataSuccess {
    private Long roomId;
    private Long userId;
    private String hostName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @Builder
    public RoomMetadataSuccess(Long roomId, Long userId, String hostName, LocalDateTime createdAt) {
        this.roomId = roomId;
        this.userId = userId;
        this.hostName = hostName;
        this.createdAt = createdAt;
    }
}
