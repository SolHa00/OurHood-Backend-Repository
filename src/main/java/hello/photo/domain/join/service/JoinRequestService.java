package hello.photo.domain.join.service;

import hello.photo.domain.join.dto.response.JoinRequestDetail;
import hello.photo.domain.join.dto.response.JoinResponseDto;
import hello.photo.domain.join.entity.JoinRequest;
import hello.photo.domain.join.repository.JoinRequestRepository;
import hello.photo.domain.room.entity.Room;
import hello.photo.domain.room.repository.RoomRepository;
import hello.photo.domain.user.entity.User;
import hello.photo.domain.user.repository.UserRepository;
import hello.photo.global.exception.DuplicateRequestException;
import hello.photo.global.exception.EntityNotFoundException;
import hello.photo.global.response.ApiResponse;
import hello.photo.global.response.Code;
import hello.photo.global.response.DataResponseDto;
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

    public ApiResponse createJoinRequest(Long roomId, Long userId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("해당 Room을 찾을 수 없습니다"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 회원을 찾을 수 없습니다"));

        //이미 해당 방에 참여 요청이 있는지 확인
        boolean joinRequestExists = joinRequestRepository.existsByRoomAndUser(room, user);
        if (joinRequestExists) {
            throw new DuplicateRequestException("이미 해당 방에 참여 요청을 보냈습니다");
        }

        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setRoom(room);
        joinRequest.setUser(user);

        joinRequestRepository.save(joinRequest);

        return ApiResponse.of(Code.OK.getMessage());
    }

    public DataResponseDto<JoinResponseDto> getJoinRequests(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("해당 Room을 찾을 수 없습니다"));

        List<JoinRequestDetail> joinList = joinRequestRepository.findByRoom(room).stream()
                .map(joinRequest -> new JoinRequestDetail(joinRequest.getId(), joinRequest.getUser().getNickname()))
                .collect(Collectors.toList());

        JoinResponseDto joinResponseDto = new JoinResponseDto();
        joinResponseDto.setJoinList(joinList);

        return DataResponseDto.of(joinResponseDto, Code.OK.getMessage());
    }

    public ApiResponse handleJoinRequest(Long joinRequestId, String action) {
        JoinRequest joinRequest = joinRequestRepository.findById(joinRequestId)
                .orElseThrow(() -> new EntityNotFoundException("해당 참여 요청을 찾을 수 없습니다"));
        if ("accept".equals(action)) {
            Room room = joinRequest.getRoom();
            User user = joinRequest.getUser();
            room.getMembers().add(user);
            roomRepository.save(room);
            joinRequestRepository.delete(joinRequest);
            return ApiResponse.of("참여 요청이 승인 되었습니다");
        }

        joinRequestRepository.delete(joinRequest);
        return ApiResponse.of("참여 요청이 거절 되었습니다");
    }
}
