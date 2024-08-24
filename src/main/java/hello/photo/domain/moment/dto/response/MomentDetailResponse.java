package hello.photo.domain.moment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MomentDetailResponse {
    private String nickname;
    private String momentImage;
    private String momentDescription;
    private OffsetDateTime createdAt;
    private List<CommentResponse> comments;
}
