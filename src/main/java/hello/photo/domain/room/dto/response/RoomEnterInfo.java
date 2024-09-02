package hello.photo.domain.room.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomEnterInfo {
    private List<String> members;
    private List<MomentEnterInfo> moments;
    private Long numOfNewJoinRequests;

    public RoomEnterInfo(List<String> members, List<MomentEnterInfo> moments, Long numOfNewJoinRequests) {
        this.members = members;
        this.moments = moments;
        this.numOfNewJoinRequests = numOfNewJoinRequests;
    }
}
