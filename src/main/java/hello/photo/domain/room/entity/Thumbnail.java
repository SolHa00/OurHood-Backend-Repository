package hello.photo.domain.room.entity;

import hello.photo.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Thumbnail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String thumbnailUrl;

    @ManyToOne
    private User user;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Room room;

    @Builder
    public Thumbnail(String thumbnailUrl, User user) {
        this.thumbnailUrl = thumbnailUrl;
        this.user = user;
    }

    public void assignRoom(Room room) {
        this.room = room;
    }
}
