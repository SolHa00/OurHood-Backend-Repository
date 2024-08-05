package hello.photo.domain.user.service;

import hello.photo.domain.invitation.dto.InvitationInfo;
import hello.photo.domain.invitation.repository.InvitationRepository;
import hello.photo.domain.refresh.entity.RefreshToken;
import hello.photo.domain.refresh.repository.RefreshTokenRepository;
import hello.photo.domain.room.dto.RoomInfo;
import hello.photo.domain.user.dto.request.UserLoginRequest;
import hello.photo.domain.user.dto.request.UserSignupRequest;
import hello.photo.domain.user.dto.response.MyPageResponse;
import hello.photo.domain.user.dto.response.UserLoginResponse;
import hello.photo.domain.user.entity.User;
import hello.photo.domain.user.repository.UserRepository;
import hello.photo.global.auth.jwt.JwtUtil;
import hello.photo.global.response.Code;
import hello.photo.global.response.DataResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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

    public void signup(UserSignupRequest request) {
        User user = User.builder()
                .nickname(request.getNickname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        userRepository.save(user);
    }

    public DataResponse<UserLoginResponse> login(UserLoginRequest request, HttpServletResponse response) {
        try {
            UserDetails userData = customUserDetailsService.loadUserByUsername(request.getEmail());
            if (!passwordEncoder.matches(request.getPassword(), userData.getPassword())) {
                return DataResponse.onFailure(null, "비밀번호가 일치하지 않음");
            }

            Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
            if(userOptional.isEmpty()) {
                return DataResponse.onFailure(null, "해당 아이디가 존재하지 않음");
            }
            User user = userOptional.get();


            // 토큰 생성
            String accessToken = jwtUtil.createJwt("accessToken", user.getEmail(), 1000 * 60 * 120L); // 120분
            String refreshToken = jwtUtil.createJwt("refreshToken", user.getEmail(), 1000 * 60 * 60 * 24 * 2L); // 2일
            addRefreshToken(user.getEmail(), refreshToken, 1000 * 60 * 60 * 24 * 2L); // 2일

            // 응답 생성
            response.setHeader("accessToken", accessToken);
            response.addCookie(createCookie("refreshToken", refreshToken));

            UserLoginResponse userLoginResponse = new UserLoginResponse(user.getId(), user.getEmail(), user.getNickname());
            return DataResponse.onSuccess(userLoginResponse, Code.OK.getMessage());
        }
        catch (Exception e){
            return DataResponse.onFailure(null, Code.NOT_FOUND.getMessage());
        }
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
