package hello.photo.domain.join.entity;

import hello.photo.domain.room.entity.Room;
import hello.photo.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter //추후에 삭제할 예정
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class JoinRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Room room;

    @ManyToOne
    private User user;

    @Builder
    public JoinRequest(Room room, User user) {
        this.room = room;
        this.user = user;
    }
}
