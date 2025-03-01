package server.photo.domain.invitation.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.photo.domain.invitation.entity.Invitation;
import server.photo.domain.invitation.repository.InvitationRepository;
import server.photo.domain.room.entity.Room;

@Service
@RequiredArgsConstructor
public class InvitationCheckService {

    private final InvitationRepository invitationRepository;

    public boolean hasPendingInvitation(Room room, Long userId){
        return invitationRepository.existsByRoomAndUserId(room, userId);
    }

    public Long getInvitationId(Long userId, Room room) {
        Invitation invitation = invitationRepository.findByUserIdAndRoom(userId, room);
        return invitation.getId();
    }
}
