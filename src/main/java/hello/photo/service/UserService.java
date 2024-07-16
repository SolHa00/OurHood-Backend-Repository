package hello.photo.service;

import hello.photo.config.jwt.JwtTokenProvider;
import hello.photo.domain.User;
import hello.photo.dto.user.request.UserLoginRequest;
import hello.photo.dto.user.response.UserLoginResponse;
import hello.photo.dto.user.request.UserSignupRequest;
import hello.photo.dto.user.response.UserSignupResponse;
import hello.photo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public UserSignupResponse signup(UserSignupRequest request) {
        User user = User.builder()
                .nickname(request.getNickname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        user = userRepository.save(user);

        String token = jwtTokenProvider.createToken(user.getEmail());

        return new UserSignupResponse(user.getId(), token);
    }

    public UserLoginResponse login(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 이메일입니다."));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("이메일이나 비밀번호가 틀렸습니다.");
        }

        String token = jwtTokenProvider.createToken(user.getEmail());

        return new UserLoginResponse(user.getId(), token);
    }
}
