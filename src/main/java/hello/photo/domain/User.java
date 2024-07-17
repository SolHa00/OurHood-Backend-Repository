package hello.photo.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name="users")
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 아무런 매개변수가 없는 생성자를 생성하되 다른 패키지에 소속된 클래스는 접근을 불허
@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    private String email;

    private String password;

    private String token;

    @Builder
    public User(String nickname, String email, String password, String token) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.token = token;
    }
}