package server.photo.domain.refresh.controller;

import server.photo.domain.refresh.repository.RefreshTokenRepository;
import server.photo.global.handler.BaseException;
import server.photo.global.handler.response.BaseResponseStatus;
import server.photo.global.jwt.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReissueController {

    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    @PostMapping("/api/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {

        //get refresh token
        String refreshToken = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refreshToken")) {
                refreshToken = cookie.getValue();
            }
        }

        if (refreshToken == null) {
            //response status code
            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);
        }

        //expired check
        try {
            jwtUtil.isExpired(refreshToken);
        } catch (ExpiredJwtException e) {
            //response status code
            refreshTokenRepository.deleteByRefresh(refreshToken);
            throw new BaseException(BaseResponseStatus.REFRESH_TOKEN_EXPIRED);
        }

        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refreshToken);

        if (!category.equals("refreshToken")) {
            //response status code
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        //DB에 저장되어 있는지 확인
        Boolean isExist = refreshTokenRepository.existsByRefresh(refreshToken);
        if (!isExist) {
            //response body
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        String email = jwtUtil.getEmail(refreshToken);

        //make new JWT
        String newAccessToken = jwtUtil.createJwt("accessToken", email, 1000 * 60 * 60 *2L); // 2시간

        //response
        response.setHeader("accessToken", newAccessToken);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
