package hello.photo.domain.moment.entity;

import hello.photo.domain.comment.entity.Comment;
import hello.photo.domain.room.entity.Room;
import hello.photo.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
public class Moment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    private String momentDescription;

    private OffsetDateTime createdAt;

    @ManyToOne
    private User user;

    @ManyToOne
    private Room room;

    @OneToMany(mappedBy = "moment")
    private List<Comment> comments;

    @Builder
    public Moment(String imageUrl, String momentDescription, User user, Room room) {
        this.imageUrl = imageUrl;
        this.momentDescription = momentDescription;
        this.user = user;
        this.room = room;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = OffsetDateTime.now();
    }
}
