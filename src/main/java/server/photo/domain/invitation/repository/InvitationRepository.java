package server.photo.domain.invitation.repository;

import server.photo.domain.invitation.entity.Invitation;
import server.photo.domain.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    List<Invitation> findByUserId(Long userId);
    boolean existsByRoomAndUserId(Room room, Long userId);
    List<Invitation> findByRoom(Room room);

    Invitation findByUserIdAndRoom(Long userId, Room room);
}
