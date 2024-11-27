package hello.photo.domain.room.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class RoomCreateRequest {
    private Long userId;
    private String roomName;
    private String roomDescription;
    private MultipartFile thumbnail;
}
