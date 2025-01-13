package server.photo.domain.room.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MomentInfo {
    private Long momentId;
    private String imageUrl;

    @Builder
    public MomentInfo(Long momentId, String imageUrl) {
        this.momentId = momentId;
        this.imageUrl = imageUrl;
    }
}
