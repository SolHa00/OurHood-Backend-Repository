package hello.photo.domain.comment.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentCreateRequest {
    private Long momentId;
    private Long userId;
    private String commentContent;
}
