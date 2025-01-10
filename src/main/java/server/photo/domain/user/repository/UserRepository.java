package server.photo.domain.user.repository;

import server.photo.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    Optional<User> findByNickname(String nickname);

    Boolean existsByEmail(String email);
    Boolean existsByNickname(String nickname);
}
