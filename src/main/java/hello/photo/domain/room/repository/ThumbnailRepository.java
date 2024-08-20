package hello.photo.domain.room.repository;

import hello.photo.domain.room.entity.Room;
import hello.photo.domain.room.entity.Thumbnail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThumbnailRepository extends JpaRepository<Thumbnail, Long> {
    Optional<Thumbnail> findByRoom(Room room);
}
