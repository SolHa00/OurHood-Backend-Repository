package hello.photo.domain.join.service;

import hello.photo.domain.join.dto.response.JoinRequestDetail;
import hello.photo.domain.join.dto.response.JoinResponseDto;
import hello.photo.domain.join.entity.JoinRequest;
import hello.photo.domain.join.repository.JoinRequestRepository;
import hello.photo.domain.room.entity.Room;
import hello.photo.domain.room.repository.RoomRepository;
import hello.photo.domain.user.entity.User;
import hello.photo.domain.user.repository.UserRepository;
import hello.photo.global.exception.DuplicateException;
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
                .orElseThrow(() -> new EntityNotFoundException(Code.NOT_FOUND, Code.NOT_FOUND.getMessage()));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(Code.NOT_FOUND, Code.NOT_FOUND.getMessage()));

        //이미 해당 방에 참여 요청이 있는지 확인
        boolean joinRequestExists = joinRequestRepository.existsByRoomAndUser(room, user);
        if (joinRequestExists) {
            throw new DuplicateException(Code.JOIN_REQUEST_DUPLICATED, Code.JOIN_REQUEST_DUPLICATED.getMessage());
        }

        JoinRequest joinRequest = JoinRequest.builder()
                .room(room)
                .user(user)
                .build();

        joinRequestRepository.save(joinRequest);

        return ApiResponse.of(Code.OK.getMessage());
    }

    public DataResponseDto<JoinResponseDto> getJoinRequests(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException(Code.NOT_FOUND, Code.NOT_FOUND.getMessage()));

        List<JoinRequestDetail> joinList = joinRequestRepository.findByRoom(room).stream()
                .map(joinRequest -> JoinRequestDetail.builder()
                        .joinId(joinRequest.getId())
                        .nickName(joinRequest.getUser().getNickname())
                        .build())
                .collect(Collectors.toList());

        JoinResponseDto joinResponseDto = new JoinResponseDto(joinList);

        return DataResponseDto.of(joinResponseDto, Code.OK.getMessage());
    }

    public ApiResponse handleJoinRequest(Long joinRequestId, String action) {
        JoinRequest joinRequest = joinRequestRepository.findById(joinRequestId)
                .orElseThrow(() -> new EntityNotFoundException(Code.NOT_FOUND, Code.NOT_FOUND.getMessage()));
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
