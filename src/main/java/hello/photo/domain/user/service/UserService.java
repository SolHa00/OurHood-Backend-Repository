package hello.photo.domain.user.service;

import hello.photo.domain.user.entity.User;
import hello.photo.domain.user.dto.request.UserLoginRequest;
import hello.photo.domain.user.dto.request.UserSignupRequest;
import hello.photo.domain.user.dto.response.MyPageResponse;
import hello.photo.domain.user.dto.response.UserLoginResponse;
import hello.photo.domain.user.dto.response.UserSignupResponse;
import hello.photo.domain.user.repository.UserRepository;
import hello.photo.domain.invitation.dto.InvitationInfo;
import hello.photo.global.auth.JwtTokenProvider;
import hello.photo.domain.invitation.repository.InvitationRepository;
import hello.photo.domain.room.dto.RoomInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final InvitationRepository invitationRepository;

    public UserSignupResponse signup(UserSignupRequest request) {
        User user = User.builder()
                .nickname(request.getNickname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        user = userRepository.save(user);

        String token = jwtTokenProvider.createToken(user, Duration.ofHours(2));
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
        if (user.getToken() != null && jwtTokenProvider.validToken(user.getToken())) {
            token = user.getToken(); // 기존 토큰 재사용
        } else {
            token = jwtTokenProvider.createToken(user, Duration.ofHours(2)); // 새 토큰 생성
            user.setToken(token); // 새 토큰 저장
            userRepository.save(user); // 업데이트된 사용자 저장
        }

        return new UserLoginResponse(user.getId(), user.getNickname(), token);
    }



    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    public MyPageResponse getMyPage(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 유저 존재하지 않음"));

        List<RoomInfo> hostedRooms = user.getHostedRooms().stream()
                .map(room -> new RoomInfo(
                        room.getId(),
                        room.getRoomName(),
                        room.getHost().getNickname(),
                        room.getMembers().size(),
                        room.getCreatedAt()))
                .collect(Collectors.toList());

        List<InvitationInfo> invitations = invitationRepository.findByUser(user).stream()
                .map(invitation -> new InvitationInfo(
                        invitation.getId(),
                        invitation.getCreatedAt(),
                        invitation.getRoom().getId(),
                        invitation.getRoom().getRoomName(),
                        invitation.getRoom().getHost().getNickname()))
                .collect(Collectors.toList());
        return new MyPageResponse(
                new MyPageResponse.MyInfo(user.getNickname(), user.getEmail()),
                hostedRooms, invitations);

    }
}
