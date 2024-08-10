package hello.photo.domain.invitation.service;

import hello.photo.domain.invitation.entity.Invitation;
import hello.photo.domain.invitation.repository.InvitationRepository;
import hello.photo.domain.room.entity.Room;
import hello.photo.domain.room.repository.RoomRepository;
import hello.photo.domain.user.entity.User;
import hello.photo.domain.user.repository.UserRepository;
import hello.photo.global.response.ApiResponse;
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
                .orElseThrow(() -> new RuntimeException("해당 Room 존재하지 않음"));
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new RuntimeException("해당 유저가 존재하지 않음"));

        Invitation invitation = new Invitation();
        invitation.setRoom(room);
        invitation.setUser(user);

        invitationRepository.save(invitation);

        return new ApiResponse("초대 성공");
    }


    @Transactional
    public ApiResponse handleInviteRequest(Long invitationId, String action) {
        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new RuntimeException("해당 초대 요청을 찾을 수 없음"));
        if("accept".equalsIgnoreCase(action)) {
            Room room = invitation.getRoom();
            User user = invitation.getUser();
            room.getMembers().add(user);
            roomRepository.save(room);
        }
        invitationRepository.delete(invitation);
        return new ApiResponse("초대 요청을 " + action + " 했습니다.");
    }
}