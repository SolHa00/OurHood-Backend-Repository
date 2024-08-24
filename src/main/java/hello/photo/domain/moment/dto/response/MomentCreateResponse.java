package hello.photo.domain.moment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MomentCreateResponse {
    private Long momentId;
    private String imageUrl;
}
