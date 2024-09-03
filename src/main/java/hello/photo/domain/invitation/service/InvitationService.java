package hello.photo.domain.invitation.service;

import hello.photo.domain.invitation.entity.Invitation;
import hello.photo.domain.invitation.repository.InvitationRepository;
import hello.photo.domain.room.entity.Room;
import hello.photo.domain.room.repository.RoomRepository;
import hello.photo.domain.user.entity.User;
import hello.photo.domain.user.repository.UserRepository;
import hello.photo.global.exception.DuplicateException;
import hello.photo.global.exception.EntityNotFoundException;
import hello.photo.global.response.ApiResponse;
import hello.photo.global.response.Code;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvitationService {

    private final InvitationRepository invitationRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public ApiResponse createInvitation(Long roomId, String nickname) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException(Code.NOT_FOUND, Code.NOT_FOUND.getMessage()));
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new EntityNotFoundException(Code.MEMBER_NOT_FOUND, Code.MEMBER_NOT_FOUND.getMessage()));

        //이미 초대된 사용자인지 확인
        boolean invitationExists = invitationRepository.existsByRoomAndUser(room, user);
        if (invitationExists) {
            throw new DuplicateException(Code.INVITATION_REQUEST_DUPLICATED, Code.INVITATION_REQUEST_DUPLICATED.getMessage());
        }

        Invitation invitation = Invitation.builder()
                .room(room)
                .user(user)
                .build();

        invitationRepository.save(invitation);

        return new ApiResponse(Code.OK.getMessage());
    }


    @Transactional
    public ApiResponse handleInviteRequest(Long invitationId, String action) {
        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new EntityNotFoundException(Code.NOT_FOUND, Code.NOT_FOUND.getMessage()));
        if("accept".equals(action)) {
            Room room = invitation.getRoom();
            User user = invitation.getUser();
            room.getMembers().add(user);
            roomRepository.save(room);
            invitationRepository.delete(invitation);
            return new ApiResponse("초대 요청을 승인 했습니다");
        }
        invitationRepository.delete(invitation);
        return new ApiResponse("초대 요청을 거절 했습니다");
    }
}
