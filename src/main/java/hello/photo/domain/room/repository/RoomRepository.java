package hello.photo.domain.room.repository;

import hello.photo.domain.room.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Page<Room> findByRoomNameContaining(String query, Pageable pageable);
    Page<Room> findByHostNicknameContaining(String query, Pageable pageable);
}