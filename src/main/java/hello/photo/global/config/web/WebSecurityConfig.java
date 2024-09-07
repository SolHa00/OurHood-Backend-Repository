package hello.photo.global.config.web;

import hello.photo.domain.refresh.repository.RefreshTokenRepository;
import hello.photo.global.jwt.CustomLogoutFilter;
import hello.photo.global.jwt.JwtAuthenticationFilter;
import hello.photo.global.jwt.JwtUtil;
import hello.photo.global.jwt.LoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    //AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        LoginFilter loginFilter = new LoginFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)), jwtUtil, refreshTokenRepository);
        loginFilter.setFilterProcessesUrl("/api/login"); // 필터의 작동 경로 설정
        return http
                //stateless 상태로 관리하기에 csrf 보안 비활성화
                .csrf(AbstractHttpConfigurer::disable)
                //Form 로그인 방식 비활성화
                .formLogin(AbstractHttpConfigurer::disable)
                //http basic 인증 방식 비활성화
                .httpBasic(AbstractHttpConfigurer::disable)
                //경로별 인가 작업
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/signup", "/api/login","/error", "/api/logout","/swagger-ui/**", "/v3/api-docs/**", "/api/reissue").permitAll()
                        .anyRequest().authenticated()
                )
                //세션을 stateless 상태로 관리
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //커스텀한 JWT 필터에 의해 설정된 인증 정보를 사용할 수 있게 함
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil), LoginFilter.class)
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, refreshTokenRepository), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new CustomLogoutFilter(jwtUtil, refreshTokenRepository), LogoutFilter.class)
                .build();
    }

    //비밀번호 암호화
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
