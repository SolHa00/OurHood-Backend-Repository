package hello.photo.domain.join.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinRequestCreateDto {
    private Long roomId;
    private Long userId;
}
