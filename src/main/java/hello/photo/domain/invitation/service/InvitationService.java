package hello.photo.domain.invitation.service;

import hello.photo.domain.invitation.converter.InvitationConverter;
import hello.photo.domain.invitation.dto.request.InvitationHandleRequest;
import hello.photo.domain.invitation.entity.Invitation;
import hello.photo.domain.invitation.repository.InvitationRepository;
import hello.photo.domain.room.entity.Room;
import hello.photo.domain.room.repository.RoomRepository;
import hello.photo.domain.user.entity.User;
import hello.photo.domain.user.repository.UserRepository;
import hello.photo.global.handler.BaseException;
import hello.photo.global.handler.response.BaseResponse;
import hello.photo.global.handler.response.BaseResponseStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvitationService {

    private final InvitationRepository invitationRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    @Transactional
    public BaseResponse createInvitation(Long roomId, String nickname) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND));
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND));

        //이미 초대된 사용자인지 확인
        boolean invitationExists = invitationRepository.existsByRoomAndUserId(room, user.getId());
        if (invitationExists) {
            throw new BaseException(BaseResponseStatus.INVITATION_REQUEST_DUPLICATED);
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
    public BaseResponse handleInviteRequest(Long invitationId, InvitationHandleRequest request) {
        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND));
        if("accept".equals(request.getAction())) {
            Room room = invitation.getRoom();
            User user = userRepository.findById(invitation.getUserId())
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND));
            room.addRoomMember(user);
            invitationRepository.delete(invitation);
            return BaseResponse.success();
        }
        invitationRepository.delete(invitation);
        return BaseResponse.success();
    }
}
