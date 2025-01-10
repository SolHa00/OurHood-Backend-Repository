package server.photo.domain.room.entity;

import server.photo.domain.user.entity.BaseTimeEntity;
import server.photo.domain.invitation.entity.Invitation;
import server.photo.domain.join.entity.JoinRequest;
import server.photo.domain.moment.entity.Moment;
import server.photo.domain.user.entity.User;
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

    private Long userId;
    private String roomName;
    private String roomDescription;
    private String thumbnailImage;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomMembers> roomMembers = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Moment> moments = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JoinRequest> joinRequests = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Invitation> invitations = new ArrayList<>();

    @Builder
    public Room(Long userId, String roomName, String roomDescription, String thumbnailImage) {
        this.userId = userId;
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.thumbnailImage = thumbnailImage;
    }

    public void addRoomMember(User user) {
        RoomMembers roomMembers = RoomMembers.builder()
                .user(user)
                .room(this)
                .build();
        this.roomMembers.add(roomMembers);
        user.getRooms().add(roomMembers);
    }

    public void removeRoomMember(User user) {
        this.roomMembers.removeIf(roomMembers -> roomMembers.getUser().equals(user));
        user.getRooms().removeIf(roomMembers -> roomMembers.getRoom().equals(this));
    }

    public void updateRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void updateRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }

    public void updateThumbnailImage(String thumbnailUrl) {
        this.thumbnailImage = thumbnailUrl;
    }

    public boolean isUserMember(Long userId) {
        return this.roomMembers.stream()
                .anyMatch(roomMember -> roomMember.getUser().getId().equals(userId));
    }
}
