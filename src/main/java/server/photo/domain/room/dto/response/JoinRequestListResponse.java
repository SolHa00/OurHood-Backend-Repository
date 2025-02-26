package server.photo.domain.room.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
 public class JoinRequestListResponse {
    private List<JoinRequestDetail> joinRequestList;

    @Builder
    public JoinRequestListResponse(List<JoinRequestDetail> joinRequestList) {
        this.joinRequestList = joinRequestList;
    }
}
