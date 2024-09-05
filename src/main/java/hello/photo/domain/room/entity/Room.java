package hello.photo.domain.room.entity;

import hello.photo.domain.moment.entity.Moment;
import hello.photo.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter //추후에 삭제할 예정
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomName;
    private String roomDescription;
    private OffsetDateTime createdAt;

    @ManyToOne
    private User host;

    @ManyToMany
    private List<User> members = new ArrayList<>();

    @OneToMany(mappedBy = "room")
    private List<Moment> moments;

    @OneToOne(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Thumbnail thumbnail;

    @Builder
    public Room(String roomName, String roomDescription, User host) {
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.host = host;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = OffsetDateTime.now();
    }
}
