package hello.photo.domain.moment.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MomentDetailResponse {
    private String nickname;
    private String momentImage;
    private String momentDescription;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    private List<CommentResponse> comments;
    private Long userId;

    @Builder
    public MomentDetailResponse(String nickname, String momentImage, String momentDescription, LocalDateTime createdAt, List<CommentResponse> comments, Long userId) {
        this.nickname = nickname;
        this.momentImage = momentImage;
        this.momentDescription = momentDescription;
        this.createdAt = createdAt;
        this.comments = comments;
        this.userId = userId;
    }
}
