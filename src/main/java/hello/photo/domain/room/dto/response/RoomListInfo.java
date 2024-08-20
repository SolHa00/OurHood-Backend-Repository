package hello.photo.domain.room.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
public class RoomListInfo {
    private Long roomId;
    private String roomName;
    private String hostName;
    private int numOfMembers;
    private OffsetDateTime createdAt;
    private String thumbnail;
}

