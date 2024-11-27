package hello.photo.domain.join.converter;

import hello.photo.domain.join.dto.response.JoinRequestDetail;
import hello.photo.domain.join.dto.response.JoinRequestListResponse;
import hello.photo.domain.join.entity.JoinRequest;
import hello.photo.domain.room.entity.Room;
import hello.photo.domain.user.entity.User;

import java.util.List;

public class JoinRequestConverter {

    public static JoinRequest toJoinRequest(Room room, User user) {
        return JoinRequest.builder()
                .room(room)
                .userId(user.getId())
                .build();
    }

    public static JoinRequestDetail toJoinRequestDetail(JoinRequest joinRequest, User user) {
        return JoinRequestDetail.builder()
                .joinId(joinRequest.getId())
                .nickname(user.getNickname())
                .build();
    }

    public static JoinRequestListResponse toJoinRequestListResponse(List<JoinRequestDetail> joinList) {
        return JoinRequestListResponse.builder()
                .joinList(joinList)
                .build();
    }
}
