package hello.photo.domain.comment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CommentCreateResponse {
    private Long commentId;
    private OffsetDateTime createdAt;
}
