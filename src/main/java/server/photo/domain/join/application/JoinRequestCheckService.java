package server.photo.domain.join.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.photo.domain.join.entity.JoinRequest;
import server.photo.domain.join.repository.JoinRequestRepository;
import server.photo.domain.room.entity.Room;

@Service
@RequiredArgsConstructor
public class JoinRequestCheckService {

    private final JoinRequestRepository joinRequestRepository;

    /**
     * 사용자가 특정 방에 참여 요청을 보냈는지 확인
     */
    public boolean hasPendingJoinRequest(Room room, Long userId) {
        return joinRequestRepository.existsByRoomAndUserId(room, userId);
    }

    /**
     * 사용자가 특정 방에 보낸 참여 요청의 ID 조회
     */
    public Long getJoinRequestId(Long userId, Room room) {
        JoinRequest joinRequest = joinRequestRepository.findByUserIdAndRoom(userId, room);
        return joinRequest.getId();
    }
}
