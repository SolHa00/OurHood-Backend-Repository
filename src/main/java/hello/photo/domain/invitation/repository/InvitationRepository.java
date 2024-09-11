package hello.photo.domain.invitation.repository;

import hello.photo.domain.invitation.entity.Invitation;
import hello.photo.domain.room.entity.Room;
import hello.photo.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    List<Invitation> findByUser(User user);
    boolean existsByRoomAndUser(Room room, User user);
    List<Invitation> findByRoom(Room room);
}
