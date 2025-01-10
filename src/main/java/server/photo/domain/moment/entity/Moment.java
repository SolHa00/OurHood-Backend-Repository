package server.photo.domain.moment.entity;

import server.photo.domain.comment.entity.Comment;
import server.photo.domain.room.entity.Room;
import server.photo.domain.user.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Moment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "moment_id")
    private Long id;

    private Long userId;
    private String imageUrl;
    private String momentDescription;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToMany(mappedBy = "moment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Moment(Long userId, String imageUrl, String momentDescription, Room room) {
        this.userId = userId;
        this.imageUrl = imageUrl;
        this.momentDescription = momentDescription;
        this.room = room;
    }

    public void updateMomentDescription(String momentDescription) {
        this.momentDescription = momentDescription;
    }

}
