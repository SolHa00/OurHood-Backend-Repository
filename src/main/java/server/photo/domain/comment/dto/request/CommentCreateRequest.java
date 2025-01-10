package server.photo.domain.comment.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentCreateRequest {
    private Long userId;
    private Long momentId;
    private String commentContent;
}
