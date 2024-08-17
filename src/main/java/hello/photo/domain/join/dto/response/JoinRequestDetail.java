package hello.photo.domain.join.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JoinRequestDetail {
    private Long joinId;
    private String nickName;
}
