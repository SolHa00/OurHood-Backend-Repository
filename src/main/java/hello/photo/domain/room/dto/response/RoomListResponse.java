package hello.photo.domain.room.dto.response;

import hello.photo.domain.room.dto.Rooms;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomListResponse {
    private List<Rooms> rooms;
}
