package hello.photo.domain.moment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CommentResponse {
    private String nickname;
    private String commentContent;
    private OffsetDateTime createdAt;
}
