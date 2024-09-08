package hello.photo.domain.moment.entity;

import hello.photo.domain.comment.entity.Comment;
import hello.photo.domain.room.entity.Room;
import hello.photo.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Moment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    private String momentDescription;

    private LocalDateTime createdAt;

    @ManyToOne
    private User user;

    @ManyToOne
    private Room room;

    @OneToMany(mappedBy = "moment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Moment(String imageUrl, String momentDescription, User user, Room room) {
        this.imageUrl = imageUrl;
        this.momentDescription = momentDescription;
        this.user = user;
        this.room = room;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
