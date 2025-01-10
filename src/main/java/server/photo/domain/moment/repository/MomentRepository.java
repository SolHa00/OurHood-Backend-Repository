package server.photo.domain.moment.repository;

import server.photo.domain.moment.entity.Moment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MomentRepository extends JpaRepository<Moment, Long> {
}
