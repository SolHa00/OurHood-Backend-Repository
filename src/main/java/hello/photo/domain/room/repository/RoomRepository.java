package hello.photo.domain.room.repository;

import hello.photo.domain.room.entity.Room;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByRoomNameContaining(String query, Sort sort);
    List<Room> findByHostNicknameContaining(String query, Sort sort);
}