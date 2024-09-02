package hello.photo.domain.moment.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MomentCreateResponse {
    private Long momentId;
    private String imageUrl;

    @Builder
    public MomentCreateResponse(Long momentId, String imageUrl) {
        this.momentId = momentId;
        this.imageUrl = imageUrl;
    }
}
