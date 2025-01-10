package server.photo.domain.moment.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentResponse {
    private Long commentId;
    private String nickname;
    private String commentContent;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    private Long userId;

    @Builder
    public CommentResponse(Long commentId, String nickname, String commentContent, LocalDateTime createdAt, Long userId) {
        this.commentId = commentId;
        this.nickname = nickname;
        this.commentContent = commentContent;
        this.createdAt = createdAt;
        this.userId = userId;
    }
}
