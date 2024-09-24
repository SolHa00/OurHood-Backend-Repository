package hello.photo.domain.refresh.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id", updatable = false)
    private Long id;

    private String email;
    private String refresh;
    private LocalDateTime expiration;

    @Builder
    public RefreshToken(String email, String refresh, LocalDateTime expiration) {
        this.email = email;
        this.refresh = refresh;
        this.expiration = expiration;
    }
}
