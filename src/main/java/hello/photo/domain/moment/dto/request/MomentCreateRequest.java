package hello.photo.domain.moment.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class MomentCreateRequest {
    private Long userId;
    private MultipartFile momentImage;
    private String momentDescription;
    private Long roomId;
}
