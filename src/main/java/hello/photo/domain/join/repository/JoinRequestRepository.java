package hello.photo.domain.join.repository;

import hello.photo.domain.join.entity.JoinRequest;
import hello.photo.domain.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JoinRequestRepository extends JpaRepository<JoinRequest, Long> {
    List<JoinRequest> findByRoom(Room room);
}
