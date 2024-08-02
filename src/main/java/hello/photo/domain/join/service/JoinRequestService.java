package hello.photo.domain.join.service;

import hello.photo.domain.join.entity.JoinRequest;
import hello.photo.domain.room.entity.Room;
import hello.photo.domain.user.entity.User;
import hello.photo.domain.join.dto.response.JoinResponseDto;
import hello.photo.domain.join.repository.JoinRequestRepository;
import hello.photo.domain.room.repository.RoomRepository;
import hello.photo.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JoinRequestService {
    private final JoinRequestRepository joinRequestRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public void createJoinRequest(Long roomId, Long userId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("해당 방을 찾을 수 없음"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저 찾을 수 없음"));

        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setRoom(room);
        joinRequest.setUser(user);

        joinRequestRepository.save(joinRequest);
    }

    public List<JoinResponseDto.JoinRequesDetail> getJoinRequests(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("해당 방 존재하지 않음"));

        return joinRequestRepository.findByRoom(room).stream()
                .map(joinRequest -> new JoinResponseDto.JoinRequesDetail(
                        joinRequest.getId(), joinRequest.getUser().getNickname()))
                .collect(Collectors.toList());
    }

    public void handleJoinRequest(Long joinRequestId, String action) {
        JoinRequest joinRequest = joinRequestRepository.findById(joinRequestId)
                .orElseThrow(() -> new IllegalArgumentException("해당 참여 요청을 찾을 수 없음"));

        if ("accept".equals(action)) {
            Room room = joinRequest.getRoom();
            User user = joinRequest.getUser();
            room.getMembers().add(user);
            roomRepository.save(room);
        }

        joinRequestRepository.delete(joinRequest);
    }
}