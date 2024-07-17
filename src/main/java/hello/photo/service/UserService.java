package hello.photo.service;

import hello.photo.config.jwt.TokenProvider;
import hello.photo.domain.User;
import hello.photo.dto.user.request.UserLoginRequest;
import hello.photo.dto.user.request.UserSignupRequest;
import hello.photo.dto.user.response.UserLoginResponse;
import hello.photo.dto.user.response.UserSignupResponse;
import hello.photo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public UserSignupResponse signup(UserSignupRequest request) {
        User user = User.builder()
                .nickname(request.getNickname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        user = userRepository.save(user);

        String token = tokenProvider.createToken(user, Duration.ofHours(2));
        user.setToken(token);
        userRepository.save(user);

        return new UserSignupResponse(user.getId(), token);
    }

    public UserLoginResponse login(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 이메일입니다."));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("이메일이나 비밀번호가 틀렸습니다.");
        }

        String token;
        if (user.getToken() != null && tokenProvider.validToken(user.getToken())) {
            token = user.getToken(); // 기존 토큰 재사용
        } else {
            token = tokenProvider.createToken(user, Duration.ofHours(2)); // 새 토큰 생성
            user.setToken(token); // 새 토큰 저장
            userRepository.save(user); // 업데이트된 사용자 저장
        }

        return new UserLoginResponse(user.getId(), token);
    }



    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }
}
