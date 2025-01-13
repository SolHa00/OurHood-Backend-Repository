package server.photo.domain.room.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomPrivate {
    private List<MemberInfo> members;
    private List<MomentInfo> moments;
    private Long numOfNewJoinRequests;

    @Builder
    public RoomPrivate(List<MemberInfo> members, List<MomentInfo> moments, Long numOfNewJoinRequests) {
        this.members = members;
        this.moments = moments;
        this.numOfNewJoinRequests = numOfNewJoinRequests;
    }
}
