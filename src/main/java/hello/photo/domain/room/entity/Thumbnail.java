package hello.photo.domain.room.entity;

import hello.photo.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Thumbnail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String thumbnailUrl;

    @ManyToOne
    private User user;

    @OneToOne
    private Room room;

    @Builder
    public Thumbnail(String thumbnailUrl, User user, Room room) {
        this.thumbnailUrl = thumbnailUrl;
        this.user = user;
        this.room = room;
    }
}
