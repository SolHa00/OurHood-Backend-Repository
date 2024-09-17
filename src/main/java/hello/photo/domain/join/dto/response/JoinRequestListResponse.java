package hello.photo.domain.join.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
 public class JoinRequestListResponse {
    private List<JoinRequestDetail> joinList;

    public JoinRequestListResponse(List<JoinRequestDetail> joinList) {
        this.joinList = joinList;
    }
}