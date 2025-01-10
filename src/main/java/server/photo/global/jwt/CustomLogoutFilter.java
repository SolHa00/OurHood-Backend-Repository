package server.photo.global.jwt;

import server.photo.domain.refresh.repository.RefreshTokenRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@RequiredArgsConstructor
public class CustomLogoutFilter extends GenericFilterBean {
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        String requestUri = request.getRequestURI();
        String requestMethod = request.getMethod();

        if (requestUri.equals("/api/logout") && requestMethod.equals("POST")) {
            String refreshToken = null;
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("refreshToken")) {
                    refreshToken = cookie.getValue();
                }
            }

            if (refreshToken == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            // 만료되었더라도 정상적인 로그아웃 진행
            String category;
            try {
                category = jwtUtil.getCategory(refreshToken);
            } catch (ExpiredJwtException e) {
                category = "refreshToken";
            }

            boolean isExist = refreshTokenRepository.existsByRefresh(refreshToken);
            if (isExist) {
                refreshTokenRepository.deleteByRefresh(refreshToken);
            }

            Cookie cookie = new Cookie("refreshToken", null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);

            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
