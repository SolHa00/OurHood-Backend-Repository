package server.photo.domain.room.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class RoomUpdateRequest {
    private String roomName;
    private String roomDescription;
    private MultipartFile thumbnail;
}
