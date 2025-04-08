package server.photo.domain.invitation.application;

import lombok.extern.slf4j.Slf4j;
import server.photo.domain.invitation.converter.InvitationConverter;
import server.photo.domain.invitation.dto.request.InvitationHandleRequest;
import server.photo.domain.invitation.dto.response.JoinRequestExistsResponse;
import server.photo.domain.invitation.entity.Invitation;
import server.photo.domain.invitation.repository.InvitationRepository;
import server.photo.domain.join.application.JoinRequestCheckService;
import server.photo.domain.room.entity.Room;
import server.photo.domain.room.repository.RoomRepository;
import server.photo.domain.user.entity.User;
import server.photo.domain.user.repository.UserRepository;
import server.photo.global.handler.response.BaseException;
import server.photo.global.handler.response.BaseResponse;
import server.photo.global.handler.response.BaseResponseStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvitationService {

    private final InvitationRepository invitationRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final JoinRequestCheckService joinRequestCheckService;

    @Transactional
    public BaseResponse<Object> createInvitation(Long roomId, String nickname) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_ROOM));
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_USER));

        //이미 사용자가 참여 요청을 보냈는지 확인
        if (joinRequestCheckService.hasPendingJoinRequest(room, user.getId())) {
            Long joinRequestId = joinRequestCheckService.getJoinRequestId(user.getId(), room);
            JoinRequestExistsResponse response = InvitationConverter.toJoinRequestExistsResponse(joinRequestId);

            return BaseResponse.success(BaseResponseStatus.JOIN_REQUEST_ALREADY_SENT, response);
        }

        //이미 초대된 사용자인지 확인
        boolean invitationExists = invitationRepository.existsByRoomAndUserId(room, user.getId());
        if (invitationExists) {
            throw new BaseException(BaseResponseStatus.DUPLICATE_INVITATION_REQUEST);
        }

        // 이미 해당 방의 멤버인지 확인
        boolean isMember = room.isUserMember(user.getId());
        if (isMember) {
            throw new BaseException(BaseResponseStatus.ALREADY_INVITED_USER);
        }

        Invitation invitation = InvitationConverter.toInvitation(room, user);
        invitationRepository.save(invitation);
        return BaseResponse.success();
    }

    @Transactional
    public BaseResponse<Object> handleInviteRequest(Long invitationId, InvitationHandleRequest request) {
        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_INVITATION));
        if("accept".equals(request.getAction())) {
            Room room = invitation.getRoom();
            User user = invitation.getUser();
            room.addRoomMember(user);
            invitationRepository.delete(invitation);
            return BaseResponse.success();
        }
        invitationRepository.delete(invitation);
        return BaseResponse.success();
    }

    @Transactional
    public BaseResponse<Object> deleteInvitation(Long invitationId) {
        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_INVITATION));
        invitationRepository.delete(invitation);
        return BaseResponse.success();
    }
}
