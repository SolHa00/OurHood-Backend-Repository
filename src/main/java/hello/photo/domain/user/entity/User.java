package hello.photo.domain.user.entity;

import hello.photo.domain.comment.entity.Comment;
import hello.photo.domain.invitation.entity.Invitation;
import hello.photo.domain.room.entity.Room;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED) // 아무런 매개변수가 없는 생성자를 생성하되 다른 패키지에 소속된 클래스는 접근을 불허
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;
    private String email;
    private String password;
    private String token;

    @OneToMany(mappedBy = "host")
    private List<Room> hostedRooms;

    @ManyToMany(mappedBy = "members")
    private List<Room> rooms;

    @OneToMany(mappedBy = "commenter")
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