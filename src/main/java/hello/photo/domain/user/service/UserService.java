package hello.photo.domain.user.service;

import hello.photo.domain.invitation.dto.response.InvitationInfo;
import hello.photo.domain.invitation.repository.InvitationRepository;
import hello.photo.domain.refresh.entity.RefreshToken;
import hello.photo.domain.refresh.repository.RefreshTokenRepository;
import hello.photo.domain.room.dto.response.RoomsMyPageInfo;
import hello.photo.domain.user.dto.request.UserLoginRequest;
import hello.photo.domain.user.dto.request.UserSignupRequest;
import hello.photo.domain.user.dto.response.MyPageResponse;
import hello.photo.domain.user.dto.response.UserLoginResponse;
import hello.photo.domain.user.entity.User;
import hello.photo.domain.user.repository.UserRepository;
import hello.photo.global.jwt.JwtUtil;
import hello.photo.global.response.ApiResponse;
import hello.photo.global.response.Code;
import hello.photo.global.response.DataResponseDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final InvitationRepository invitationRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    public ApiResponse signup(UserSignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }
        if (userRepository.existsByNickname(request.getNickname())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }
        User user = User.builder()
                .nickname(request.getNickname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        userRepository.save(user);
        return ApiResponse.of("ok");
    }

    public ApiResponse login(UserLoginRequest request, HttpServletResponse response) {
        UserDetails userData = customUserDetailsService.loadUserByUsername(request.getEmail());
        if (!passwordEncoder.matches(request.getPassword(), userData.getPassword())) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return ApiResponse.of("아이디 또는 비밀번호가 잘못 되었습니다. 아이디와 비밀번호를 정확히 입력해 주세요.");
        }

        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        User user = userOptional.get();

        // 토큰 생성
        String accessToken = jwtUtil.createJwt("accessToken", user.getEmail(), 1000 * 60 * 120L); // 120분
        String refreshToken = jwtUtil.createJwt("refreshToken", user.getEmail(), 1000 * 60 * 60 * 24 * 2L); // 2일
        addRefreshToken(user.getEmail(), refreshToken, 1000 * 60 * 60 * 24 * 2L); // 2일

        // 응답 생성
        response.setHeader("accessToken", accessToken);
        response.addCookie(createCookie("refreshToken", refreshToken));

        UserLoginResponse userLoginResponse = new UserLoginResponse(user.getId(), user.getEmail(), user.getNickname());
        return DataResponseDto.of(userLoginResponse, Code.OK.getMessage());

    }

    public DataResponseDto<MyPageResponse> getMyPage(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 유저 존재하지 않음"));

        List<RoomsMyPageInfo> hostedRooms = user.getHostedRooms().stream()
                .map(room -> new RoomsMyPageInfo(
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

        MyPageResponse myPageResponse = new MyPageResponse(new MyPageResponse.MyInfo(user.getNickname(), user.getEmail()), hostedRooms, invitations);

        return DataResponseDto.of(myPageResponse);
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        //cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

    private void addRefreshToken(String email, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshToken refreshEntity = new RefreshToken();
        refreshEntity.setEmail(email);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setExpiration(date.toString());

        refreshTokenRepository.save(refreshEntity);
    }
}
