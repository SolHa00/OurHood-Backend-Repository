package hello.photo.domain.invitation.service;

import hello.photo.domain.invitation.entity.Invitation;
import hello.photo.domain.room.entity.Room;
import hello.photo.domain.user.entity.User;
import hello.photo.domain.invitation.repository.InvitationRepository;
import hello.photo.domain.room.repository.RoomRepository;
import hello.photo.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvitationService {

    private final InvitationRepository invitationRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public void createInvitation(Long roomId, String nickname) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("해당 Room 존재하지 않음"));
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new RuntimeException("해당 닉네임을 가진 User 존재하지 않음"));

        Invitation invitation = new Invitation();
        invitation.setRoom(room);
        invitation.setUser(user);

        invitationRepository.save(invitation);
    }
}

