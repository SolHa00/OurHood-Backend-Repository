package hello.photo.domain.room.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RoomCreateResponse {
    private Long roomId;
    private String thumbnail;
}
