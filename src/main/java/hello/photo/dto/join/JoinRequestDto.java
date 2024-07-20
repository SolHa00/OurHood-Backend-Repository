package hello.photo.dto.join;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinRequestDto {
    private Long roomId;
    private Long userId;
}
