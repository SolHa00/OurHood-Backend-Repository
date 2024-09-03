package hello.photo.domain.refresh.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    private String email;
    private String refresh;
    private String expiration;

    @Builder
    public RefreshToken(String email, String refresh, String expiration) {
        this.email = email;
        this.refresh = refresh;
        this.expiration = expiration;
    }
}
