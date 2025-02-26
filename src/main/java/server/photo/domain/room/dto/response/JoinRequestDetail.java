package server.photo.domain.room.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JoinRequestDetail {
    private Long joinRequestId;
    private String nickname;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @Builder
    public JoinRequestDetail(Long joinRequestId, String nickname, LocalDateTime createdAt) {
        this.joinRequestId = joinRequestId;
        this.nickname = nickname;
        this.createdAt = createdAt;
    }
}
