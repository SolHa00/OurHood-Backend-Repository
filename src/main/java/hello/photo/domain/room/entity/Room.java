package hello.photo.domain.room.entity;

import hello.photo.domain.BaseTimeEntity;
import hello.photo.domain.invitation.entity.Invitation;
import hello.photo.domain.join.entity.JoinRequest;
import hello.photo.domain.moment.entity.Moment;
import hello.photo.domain.user.entity.User;
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
public class Room extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    private String roomName;
    private String roomDescription;
    private String thumbnailImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomMembers> roomMembers = new ArrayList<>();

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

    public void updateThumbnailImage(String thumbnailUrl) {
        this.thumbnailImage = thumbnailUrl;
    }

    public void addRoomMember(User user) {
        RoomMembers roomMembers = RoomMembers.builder()
                .user(user)
                .room(this)
                .build();
        this.roomMembers.add(roomMembers);
        user.getRooms().add(roomMembers);
    }
}
