package hello.photo.domain.join.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
 public class JoinResponseDto {
    private List<JoinRequestDetail> joinList;

    public JoinResponseDto(List<JoinRequestDetail> joinList) {
        this.joinList = joinList;
    }
}
