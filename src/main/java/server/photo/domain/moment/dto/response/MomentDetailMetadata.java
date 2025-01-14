package server.photo.domain.moment.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MomentDetailMetadata {
    private String momentImage;
    private String nickname;
    private Long userId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @Builder
    public MomentDetailMetadata(String momentImage, String nickname, Long userId, LocalDateTime createdAt) {
        this.momentImage = momentImage;
        this.nickname = nickname;
        this.userId = userId;
        this.createdAt = createdAt;
    }
}
