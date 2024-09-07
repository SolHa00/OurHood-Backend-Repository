package hello.photo.domain.comment.entity;

import hello.photo.domain.moment.entity.Moment;
import hello.photo.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private OffsetDateTime createdAt;

    @ManyToOne
    private Moment moment;

    @ManyToOne
    private User user;

    @Builder
    public Comment(String content, Moment moment, User user) {
        this.content = content;
        this.moment = moment;
        this.user = user;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = OffsetDateTime.now(ZoneOffset.ofHours(9));
    }
}
