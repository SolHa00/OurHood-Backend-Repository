package server.photo.domain.moment.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MomentDetailResponse {
    private MomentDetailMetadata momentMetadata;
    private MomentDetail momentDetail;
    private List<CommentResponse> comments;

    @Builder
    public MomentDetailResponse(MomentDetailMetadata momentMetadata, MomentDetail momentDetail, List<CommentResponse> comments) {
        this.momentMetadata = momentMetadata;
        this.momentDetail = momentDetail;
        this.comments = comments;
    }
}
