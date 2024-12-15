package hello.photo.domain.join.service;

import hello.photo.domain.join.converter.JoinRequestConverter;
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
import hello.photo.global.handler.BaseException;
import hello.photo.global.handler.response.BaseResponse;
import hello.photo.global.handler.response.BaseResponseStatus;
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
    public BaseResponse createJoinRequest(JoinRequestCreateDto request) {
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND));

        //이미 해당 방에 참여 요청이 있는지 확인
        boolean joinRequestExists = joinRequestRepository.existsByRoomAndUserId(room, user.getId());
        if (joinRequestExists) {
            throw new BaseException(BaseResponseStatus.JOIN_REQUEST_DUPLICATED);
        }

        JoinRequest joinRequest = JoinRequestConverter.toJoinRequest(room, user);

        joinRequestRepository.save(joinRequest);

        return BaseResponse.success();
    }

    //방 참여 요청 목록
    public BaseResponse<JoinRequestListResponse> getJoinRequests(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND));

        List<JoinRequest> joinRequests = joinRequestRepository.findByRoom(room);
        List<JoinRequestDetail> joinList = new ArrayList<>();

        for (JoinRequest joinRequest : joinRequests) {
            User user = userRepository.findById(joinRequest.getUserId())
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND));
            JoinRequestDetail joinRequestDetail = JoinRequestConverter.toJoinRequestDetail(joinRequest, user);
            joinList.add(joinRequestDetail);
        }

        JoinRequestListResponse joinResponseDto = JoinRequestConverter.toJoinRequestListResponse(joinList);

        return BaseResponse.success(joinResponseDto);
    }

    //방 참여 요청 처리
    @Transactional
    public BaseResponse handleJoinRequest(Long joinRequestId, JoinRequestHandleDto request) {
        JoinRequest joinRequest = joinRequestRepository.findById(joinRequestId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND));
        if ("accept".equals(request.getAction())) {
            Room room = joinRequest.getRoom();
            User user = userRepository.findById(joinRequest.getUserId())
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND));
            room.addRoomMember(user);
            joinRequestRepository.delete(joinRequest);
            return BaseResponse.success();
        }

        joinRequestRepository.delete(joinRequest);
        return BaseResponse.success();
    }
}
