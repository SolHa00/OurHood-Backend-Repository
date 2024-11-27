package hello.photo.domain.join.service;

import hello.photo.domain.join.dto.request.JoinRequestCreateDto;
import hello.photo.domain.join.dto.request.JoinRequestHandleDto;
import hello.photo.domain.join.dto.response.JoinRequestDetail;
import hello.photo.domain.join.dto.response.JoinRequestListResponse;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JoinRequestService {
    private final JoinRequestRepository joinRequestRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    //방 참여 요청 생성
    @Transactional
    public ApiResponse createJoinRequest(JoinRequestCreateDto request) {
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new EntityNotFoundException(Code.NOT_FOUND, Code.NOT_FOUND.getMessage()));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(Code.NOT_FOUND, Code.NOT_FOUND.getMessage()));

        //이미 해당 방에 참여 요청이 있는지 확인
        boolean joinRequestExists = joinRequestRepository.existsByRoomAndUserId(room, user.getId());
        if (joinRequestExists) {
            throw new DuplicateException(Code.JOIN_REQUEST_DUPLICATED, Code.JOIN_REQUEST_DUPLICATED.getMessage());
        }

        JoinRequest joinRequest = JoinRequest.builder()
                .room(room)
                .userId(user.getId())
                .build();

        joinRequestRepository.save(joinRequest);

        return ApiResponse.of(Code.OK.getMessage());
    }

    //방 참여 요청 목록
    public DataResponseDto<JoinRequestListResponse> getJoinRequests(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException(Code.NOT_FOUND, Code.NOT_FOUND.getMessage()));

        List<JoinRequest> joinRequests = joinRequestRepository.findByRoom(room);
        List<JoinRequestDetail> joinList = new ArrayList<>();

        for (JoinRequest joinRequest : joinRequests) {
            User user = userRepository.findById(joinRequest.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException(Code.NOT_FOUND, Code.NOT_FOUND.getMessage()));
            JoinRequestDetail joinRequestDetail = JoinRequestDetail.builder()
                    .joinId(joinRequest.getId())
                    .nickname(user.getNickname())
                    .build();

            joinList.add(joinRequestDetail);
        }

        JoinRequestListResponse joinResponseDto = new JoinRequestListResponse(joinList);

        return DataResponseDto.of(joinResponseDto, Code.OK.getMessage());
    }

    //방 참여 요청 처리
    @Transactional
    public ApiResponse handleJoinRequest(Long joinRequestId, JoinRequestHandleDto request) {
        JoinRequest joinRequest = joinRequestRepository.findById(joinRequestId)
                .orElseThrow(() -> new EntityNotFoundException(Code.NOT_FOUND, Code.NOT_FOUND.getMessage()));
        if ("accept".equals(request.getAction())) {
            Room room = joinRequest.getRoom();
            User user = userRepository.findById(joinRequest.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException(Code.NOT_FOUND, Code.NOT_FOUND.getMessage()));
            room.addRoomMember(user);
            joinRequestRepository.delete(joinRequest);
            return ApiResponse.of("참여 요청이 승인 되었습니다");
        }

        joinRequestRepository.delete(joinRequest);
        return ApiResponse.of("참여 요청이 거절 되었습니다");
    }
}
