package server.photo.domain.room.repository;

import server.photo.domain.room.entity.Room;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByRoomNameContaining(String query, Sort sort);

    @Query("SELECT r FROM Room r JOIN User u ON r.userId = u.id WHERE u.nickname LIKE %:query%")
    List<Room> findByUserNicknameContaining(@Param("query") String query, Sort sort);
}
