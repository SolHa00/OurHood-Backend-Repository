package hello.photo.domain.room.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RoomListResponse {
    private List<RoomListInfo> rooms;
}
