package hello.photo.domain.refresh.controller;

import hello.photo.domain.refresh.repository.RefreshTokenRepository;
import hello.photo.global.exception.GeneralException;
import hello.photo.global.jwt.JwtUtil;
import hello.photo.global.response.Code;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name="AccessToken 재발급 요청 API", description = "AccessToken이 더이상 유효하지 않을때 요청")
public class ReissueController {

    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    @Operation(summary = "AccessToken 재발급", description = "유효하지 않은 AccessToken을 RefreshToken을 통해 재발급",
            parameters = {
                    @Parameter(name = "refreshToken", description = "Refresh Token", required = true, in = ParameterIn.COOKIE, schema = @Schema(type = "string"))
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "토큰 재발급 성공")
            })
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
            throw new GeneralException(Code.REFRESH_TOKEN_EXPIRED, Code.REFRESH_TOKEN_EXPIRED.getMessage());
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
        String newAccessToken = jwtUtil.createJwt("accessToken", email, 1000 * 60 * 3L); // 3분

        //response
        response.setHeader("accessToken", newAccessToken);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
