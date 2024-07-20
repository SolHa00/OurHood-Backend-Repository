package hello.photo.repository;

import hello.photo.domain.Moment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//사진, 댓글을 담을 수 있는 객체 저장소
@Repository
public interface MomentRepository extends JpaRepository<Moment, Long> {
}
