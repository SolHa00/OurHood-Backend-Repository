package hello.photo.domain.room.entity;

import hello.photo.domain.invitation.entity.Invitation;
import hello.photo.domain.join.entity.JoinRequest;
import hello.photo.domain.moment.entity.Moment;
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
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomName;
    private String roomDescription;
    private LocalDateTime createdAt;
    private String thumbnailImage;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany // 추후에 수정 예정 -> 1대다 다대1 중간 테이블 생성 예정
    private List<User> members = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Moment> moments = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JoinRequest> joinRequests = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Invitation> invitations = new ArrayList<>();

    @Builder
    public Room(String roomName, String roomDescription, User user, String thumbnailImage) {
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.user = user;
        this.thumbnailImage = thumbnailImage;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public void updateThumbnailImage(String thumbnailUrl) {
        this.thumbnailImage = thumbnailUrl;
    }
}
