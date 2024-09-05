package hello.photo.domain.invitation.entity;

import hello.photo.domain.room.entity.Room;
import hello.photo.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter //추후에 삭제할 예정
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private OffsetDateTime createdAt;

    @ManyToOne
    private User user;

    @ManyToOne
    private Room room;

    @Builder
    public Invitation(User user, Room room) {
        this.user = user;
        this.room = room;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = OffsetDateTime.now();
    }
}
