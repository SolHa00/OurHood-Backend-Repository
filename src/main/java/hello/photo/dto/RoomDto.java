package hello.photo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomDto {
    private String roomName;
    private String roomDescription;
    private Long userId;
}
