package hello.photo.domain.user.entity;

import hello.photo.domain.comment.entity.Comment;
import hello.photo.domain.invitation.entity.Invitation;
import hello.photo.domain.room.entity.Room;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nickname;

    @Column(unique = true)
    private String email;
    private String password;

    @OneToMany(mappedBy = "host")
    private List<Room> hostedRooms;

    @ManyToMany(mappedBy = "members")
    private List<Room> rooms;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @OneToMany(mappedBy = "user")
    private List<Invitation> invitations;

    @Builder
    public User(String nickname, String email, String password) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }
}
