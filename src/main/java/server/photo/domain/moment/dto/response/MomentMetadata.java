package server.photo.domain.moment.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MomentMetadata {
    private Long momentId;
    private String momentImage;

    @Builder
    public MomentMetadata(Long momentId, String momentImage) {
        this.momentId = momentId;
        this.momentImage = momentImage;
    }
}
