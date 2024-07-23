package hello.photo.repository;

import hello.photo.domain.Invitation;
import hello.photo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    List<Invitation> findByUser(User user);
}
