package hello.photo.domain.comment.entity;

import hello.photo.domain.moment.entity.Moment;
import hello.photo.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String content;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "moment_id")
    private Moment moment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Comment(String content, Moment moment, User user) {
        this.content = content;
        this.moment = moment;
        this.user = user;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
