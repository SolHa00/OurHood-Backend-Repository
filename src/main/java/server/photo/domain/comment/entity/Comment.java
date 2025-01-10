package server.photo.domain.comment.entity;

import server.photo.domain.moment.entity.Moment;
import server.photo.domain.user.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private Long userId;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moment_id")
    private Moment moment;

    @Builder
    public Comment(Long userId, String content, Moment moment) {
        this.userId = userId;
        this.content = content;
        this.moment = moment;
    }

    public void updateContent(String content) {
        this.content = content;
    }

}
