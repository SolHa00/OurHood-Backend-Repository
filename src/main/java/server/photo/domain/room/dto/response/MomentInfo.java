package server.photo.domain.room.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MomentInfo {
    private Long momentId;
    private String momentImage;

    @Builder
    public MomentInfo(Long momentId, String momentImage) {
        this.momentId = momentId;
        this.momentImage = momentImage;
    }
}
