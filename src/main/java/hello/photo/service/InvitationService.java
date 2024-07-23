package hello.photo.service;

import hello.photo.domain.Invitation;
import hello.photo.domain.Room;
import hello.photo.domain.User;
import hello.photo.repository.InvitationRepository;
import hello.photo.repository.RoomRepository;
import hello.photo.repository.UserRepository;
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

