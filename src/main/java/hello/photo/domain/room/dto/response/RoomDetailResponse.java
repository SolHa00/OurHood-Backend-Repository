package hello.photo.domain.room.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RoomDetailResponse {
    @JsonProperty("isMember")
    private boolean isMember;
    private Long roomId;
    private String roomName;
    private String roomDescription;
    private String hostName;
    private RoomEnterInfo roomDetail;
}