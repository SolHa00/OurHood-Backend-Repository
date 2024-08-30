package hello.photo.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.photo.domain.user.dto.CustomUserDetails;
import hello.photo.domain.user.entity.User;
import hello.photo.global.response.Code;
import hello.photo.global.response.ErrorResponseDto;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

//OncePerRequestFilter -> 요청당 한번만 호출되도록 보장
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        // 특정 경로는 필터링하지 않음
        if ("/api/reissue".equals(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 헤더에서 access 키에 담긴 토큰을 꺼냄
        String accessToken = request.getHeader("accessToken");

        // 토큰이 없다면 다음 필터로 넘김
        if (accessToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰 만료 여부 확인, 만료시 다음 필터로 넘기지 않음
        try {
            jwtUtil.isExpired(accessToken);
        } catch (ExpiredJwtException e) {
            // 필터 내에서 JSON 형식으로 응답 처리
            ErrorResponseDto errorResponse = ErrorResponseDto.of(Code.ACCESS_TOKEN_EXPIRED, Code.ACCESS_TOKEN_EXPIRED.getMessage());

            // 응답의 상태 코드 설정
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401

            // 응답의 Content-Type 설정
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // 응답을 JSON 형식으로 변환하여 출력
            PrintWriter writer = response.getWriter();
            writer.write(new ObjectMapper().writeValueAsString(errorResponse));
            writer.flush();
            writer.close();

            return;
        }

        // 토큰이 access 인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(accessToken);

        if (!category.equals("accessToken")) {

            //response body
            PrintWriter writer = response.getWriter();
            writer.print("invalid access token");

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // email 값을 획득
        String email = jwtUtil.getEmail(accessToken);

        User user = new User("tempNickname", email, "tempPassword");
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);

    }
}
