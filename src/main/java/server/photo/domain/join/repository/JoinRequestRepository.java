package server.photo.domain.join.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.photo.domain.join.entity.JoinRequest;
import server.photo.domain.room.entity.Room;

import java.util.List;

@Repository
public interface JoinRequestRepository extends JpaRepository<JoinRequest, Long> {
    List<JoinRequest> findByRoom(Room room);
    boolean existsByRoomAndUserId(Room room, Long userId);
    Long countByRoom(Room room);
    List<JoinRequest> findByUserId(Long userId);

    JoinRequest findByUserIdAndRoom(Long userId, Room room);
}
