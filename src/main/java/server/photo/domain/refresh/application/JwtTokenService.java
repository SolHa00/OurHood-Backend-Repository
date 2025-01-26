package server.photo.domain.refresh.application;

import server.photo.global.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtTokenService {

    private final JwtUtil jwtUtil;

    public String createAccessToken(String email) {
        return jwtUtil.createJwt("accessToken", email, 1000 * 60 * 60 * 2L); // 2시간
    }

    public String createRefreshToken(String email) {
        return jwtUtil.createJwt("refreshToken", email, 1000 * 60 * 60 * 24 * 7L); // 7일
    }
}
