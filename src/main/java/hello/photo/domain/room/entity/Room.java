package hello.photo.domain.room.entity;

import hello.photo.domain.moment.entity.Moment;
import hello.photo.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomName;
    private String roomDescription;
    private OffsetDateTime createdAt;
    private String thumbnailImage;

    @ManyToOne
    private User host;

    @ManyToMany
    private List<User> members = new ArrayList<>();

    @OneToMany(mappedBy = "room")
    private List<Moment> moments;

    @Builder
    public Room(String roomName, String roomDescription, User host, String thumbnailImage) {
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.host = host;
        this.thumbnailImage = thumbnailImage;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = OffsetDateTime.now(ZoneOffset.ofHours(9));
    }

    public void updateThumbnailImage(String thumbnailUrl) {
        this.thumbnailImage = thumbnailUrl;
    }
}
