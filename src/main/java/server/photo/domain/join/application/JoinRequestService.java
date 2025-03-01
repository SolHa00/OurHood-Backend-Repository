package server.photo.domain.join.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.photo.domain.invitation.application.InvitationCheckService;
import server.photo.domain.join.converter.JoinRequestConverter;
import server.photo.domain.join.dto.request.JoinRequestCreateDto;
import server.photo.domain.join.dto.request.JoinRequestHandleDto;
import server.photo.domain.join.dto.response.InvitationExistsResponse;
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
    private final InvitationCheckService invitationCheckService;

    //방 참여 요청 생성
    @Transactional
    public BaseResponse<Object> createJoinRequest(JoinRequestCreateDto request) {
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_ROOM));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_USER));

        //초대 여부 확인
        if (invitationCheckService.hasPendingInvitation(room, user.getId())) {
            Long invitationId = invitationCheckService.getInvitationId(user.getId(), room);
            InvitationExistsResponse response = JoinRequestConverter.toInvitationExistsResponse(invitationId);

            return BaseResponse.success(BaseResponseStatus.INVITATION_ALREADY_SENT, response);
        }

        //이미 해당 방에 참여 요청이 있는지 확인
        boolean joinRequestExists = joinRequestRepository.existsByRoomAndUserId(room, user.getId());
        if(joinRequestExists) {
            throw new BaseException(BaseResponseStatus.DUPLICATE_JOIN_REQUEST);
        }

        JoinRequest joinRequest = JoinRequestConverter.toJoinRequest(room, user);
        joinRequestRepository.save(joinRequest);
        JoinRequestCreateResponse response = JoinRequestConverter.toJoinRequestCreateResponse(joinRequest);
        return BaseResponse.success(response);
    }

    //방 참여 요청 처리
    @Transactional
    public BaseResponse<Object> handleJoinRequest(Long joinRequestId, JoinRequestHandleDto request) {
        JoinRequest joinRequest = joinRequestRepository.findById(joinRequestId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_JOIN_REQUEST));
        if ("accept".equals(request.getAction())) {
            Room room = joinRequest.getRoom();
            User user = userRepository.findById(joinRequest.getUserId())
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_USER));
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
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_JOIN_REQUEST));
        joinRequestRepository.delete(joinRequest);
        return BaseResponse.success();
    }
}
