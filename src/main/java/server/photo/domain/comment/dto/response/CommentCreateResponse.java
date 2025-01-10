package server.photo.domain.comment.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentCreateResponse {

    private Long commentId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @Builder
    public CommentCreateResponse(Long commentId, LocalDateTime createdAt) {
        this.commentId = commentId;
        this.createdAt = createdAt;
    }
}
