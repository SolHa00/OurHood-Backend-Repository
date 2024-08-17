package hello.photo.domain.join.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JoinResponseDto {
    private List<JoinRequestDetail> joinList;
}
