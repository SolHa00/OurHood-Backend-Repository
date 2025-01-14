package server.photo.domain.moment.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MomentDetail {
    private String momentDescription;

    @Builder
    public MomentDetail(String momentDescription) {
        this.momentDescription = momentDescription;
    }
}
