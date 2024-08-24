package hello.photo.domain.comment.repository;

import hello.photo.domain.comment.entity.Comment;
import hello.photo.domain.moment.entity.Moment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByMoment(Moment moment);
}
