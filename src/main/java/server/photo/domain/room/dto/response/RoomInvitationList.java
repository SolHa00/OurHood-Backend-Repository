package server.photo.domain.room.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomInvitationList {
    private Long invitationId;
    private String nickname;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @Builder
    public RoomInvitationList(Long invitationId, String nickname, LocalDateTime createdAt) {
        this.invitationId = invitationId;
        this.nickname = nickname;
        this.createdAt = createdAt;
    }
}
