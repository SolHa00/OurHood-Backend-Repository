package server.photo.domain.moment.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MomentCreateResponse {
    private MomentMetadata momentMetadata;

    @Builder
    public MomentCreateResponse(MomentMetadata momentMetadata) {
        this.momentMetadata = momentMetadata;
    }
}
