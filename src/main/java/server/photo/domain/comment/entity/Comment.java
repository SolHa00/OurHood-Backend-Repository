package server.photo.domain.comment.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.photo.domain.moment.entity.Moment;
import server.photo.domain.user.entity.BaseTimeEntity;
import server.photo.domain.user.entity.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moment_id")
    private Moment moment;

    @Builder
    public Comment(User user, String content, Moment moment) {
        this.user = user;
        this.content = content;
        this.moment = moment;
    }

    public void updateContent(String content) {
        this.content = content;
    }

}
