package server.photo.domain.refresh.service;

import server.photo.domain.refresh.entity.RefreshToken;
import server.photo.domain.refresh.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public void saveRefreshToken(String email, String refreshToken, Long expiredMs) {
        LocalDateTime expirationDate = LocalDateTime.now().plus(Duration.ofMillis(expiredMs));

        RefreshToken refreshEntity = RefreshToken.builder()
                .email(email)
                .refresh(refreshToken)
                .expiration(expirationDate)
                .build();

        refreshTokenRepository.save(refreshEntity);
    }
}
