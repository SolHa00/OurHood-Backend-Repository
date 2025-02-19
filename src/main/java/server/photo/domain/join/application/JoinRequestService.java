package server.photo.domain.join.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.photo.domain.join.converter.JoinRequestConverter;
import server.photo.domain.join.dto.request.JoinRequestCreateDto;
import server.photo.domain.join.dto.request.JoinRequestHandleDto;
import server.photo.domain.join.dto.response.JoinRequestCreateResponse;
import server.photo.domain.join.entity.JoinRequest;
import server.photo.domain.join.repository.JoinRequestRepository;
import server.photo.domain.room.entity.Room;
import server.photo.domain.room.repository.RoomRepository;
import server.photo.domain.user.entity.User;
import server.photo.domain.user.repository.UserRepository;
import server.photo.global.handler.response.BaseException;
import server.photo.global.handler.response.BaseResponse;
import server.photo.global.handler.response.BaseResponseStatus;

@Service
@RequiredArgsConstructor
public class JoinRequestService {
    private final JoinRequestRepository joinRequestRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    //방 참여 요청 생성
    @Transactional
    public BaseResponse<JoinRequestCreateResponse> createJoinRequest(JoinRequestCreateDto request) {
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.ROOM_NOT_FOUND));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.USER_NOT_FOUND));

        //이미 해당 방에 참여 요청이 있는지 확인
        boolean joinRequestExists = joinRequestRepository.existsByRoomAndUserId(room, user.getId());
        if (joinRequestExists) {
            throw new BaseException(BaseResponseStatus.JOIN_REQUEST_DUPLICATED);
        }

        JoinRequest joinRequest = JoinRequestConverter.toJoinRequest(room, user);
        JoinRequestCreateResponse response = JoinRequestConverter.toJoinRequestCreateResponse(joinRequest);

        joinRequestRepository.save(joinRequest);

        return BaseResponse.success(response);
    }

    //방 참여 요청 처리
    @Transactional
    public BaseResponse<Object> handleJoinRequest(Long joinRequestId, JoinRequestHandleDto request) {
        JoinRequest joinRequest = joinRequestRepository.findById(joinRequestId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.JOIN_REQUEST_NOT_FOUND));
        if ("accept".equals(request.getAction())) {
            Room room = joinRequest.getRoom();
            User user = userRepository.findById(joinRequest.getUserId())
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.USER_NOT_FOUND));
            room.addRoomMember(user);
            joinRequestRepository.delete(joinRequest);
            return BaseResponse.success();
        }

        joinRequestRepository.delete(joinRequest);
        return BaseResponse.success();
    }

    //방 참여 요청 삭제
    @Transactional
    public BaseResponse<Object> deleteJoinRequest(Long joinRequestId) {
        JoinRequest joinRequest = joinRequestRepository.findById(joinRequestId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.JOIN_REQUEST_NOT_FOUND));
        joinRequestRepository.delete(joinRequest);
        return BaseResponse.success();
    }
}
