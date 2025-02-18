package server.photo.domain.user.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JoinRequestInfo {

    private Long joinRequestId;
    private String roomName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @Builder
    public JoinRequestInfo(Long joinRequestId, String roomName, LocalDateTime createdAt) {
        this.joinRequestId = joinRequestId;
        this.roomName = roomName;
        this.createdAt = createdAt;
    }
}
