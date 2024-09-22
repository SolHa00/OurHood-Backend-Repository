package hello.photo.domain.user.service;

import hello.photo.domain.invitation.dto.response.InvitationInfo;
import hello.photo.domain.invitation.repository.InvitationRepository;
import hello.photo.domain.refresh.entity.RefreshToken;
import hello.photo.domain.refresh.repository.RefreshTokenRepository;
import hello.photo.domain.room.entity.Room;
import hello.photo.domain.user.dto.response.RoomsMyPageInfo;
import hello.photo.domain.user.dto.request.UserLoginRequest;
import hello.photo.domain.user.dto.request.UserSignupRequest;
import hello.photo.domain.user.dto.response.MyInfo;
import hello.photo.domain.user.dto.response.MyPageResponse;
import hello.photo.domain.user.dto.response.UserLoginInfo;
import hello.photo.domain.user.dto.response.UserLoginResponse;
import hello.photo.domain.user.entity.User;
import hello.photo.domain.user.repository.UserRepository;
import hello.photo.global.exception.DuplicateException;
import hello.photo.global.exception.EntityNotFoundException;
import hello.photo.global.exception.LogInFailException;
import hello.photo.global.jwt.JwtUtil;
import hello.photo.global.response.ApiResponse;
import hello.photo.global.response.Code;
import hello.photo.global.response.DataResponseDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public ApiResponse signup(UserSignupRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateException(Code.USER_EMAIL_DUPLICATED, Code.USER_EMAIL_DUPLICATED.getMessage());
        }
        if (userRepository.existsByNickname(request.getNickname())) {
            throw new DuplicateException(Code.USER_NICKNAME_DUPLICATED, Code.USER_NICKNAME_DUPLICATED.getMessage());
        }

        User user = User.builder()
                .nickname(request.getNickname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        userRepository.save(user);
        return ApiResponse.of(Code.OK.getMessage());
    }

    public ApiResponse login(UserLoginRequest request, HttpServletResponse response) {
        UserDetails userData = customUserDetailsService.loadUserByUsername(request.getEmail());
        if (!passwordEncoder.matches(request.getPassword(), userData.getPassword())) {
            throw new LogInFailException(Code.LOGIN_FAIL, Code.LOGIN_FAIL.getMessage());
        }

        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        User user = userOptional.get();

        // 토큰 생성
        String accessToken = jwtUtil.createJwt("accessToken", user.getEmail(), 1000 * 60 * 120L); // 2시간
        String refreshToken = jwtUtil.createJwt("refreshToken", user.getEmail(), 1000 * 60 * 60 * 24 * 14L); // 14일
        addRefreshToken(user.getEmail(), refreshToken, 1000 * 60 * 60 * 24 * 14L); // 14일

        // 응답 생성
        response.setHeader("accessToken", accessToken);
        response.addCookie(createCookie("refreshToken", refreshToken));

        UserLoginInfo userLoginInfo = UserLoginInfo.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();

        UserLoginResponse userLoginResponse = new UserLoginResponse(userLoginInfo);

        return DataResponseDto.of(userLoginResponse, Code.OK.getMessage());

    }

    public DataResponseDto<MyPageResponse> getMyPage(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Code.NOT_FOUND, Code.NOT_FOUND.getMessage()));

        List<RoomsMyPageInfo> hostedRooms = user.getRooms().stream()
                .map(roomMembers -> {
                    Room room = roomMembers.getRoom();
                    return RoomsMyPageInfo.builder()
                            .roomId(room.getId())
                            .roomName(room.getRoomName())
                            .hostName(room.getUser().getNickname())
                            .numOfMembers(room.getRoomMembers().size())
                            .createdAt(room.getCreatedAt())
                            .build();
                })
                .collect(Collectors.toList());

        List<InvitationInfo> invitations = invitationRepository.findByUser(user).stream()
                .map(invitation -> InvitationInfo.builder()
                                .invitationId(invitation.getId())
                                .createdAt(invitation.getCreatedAt())
                                .roomId(invitation.getRoom().getId())
                                .roomName(invitation.getRoom().getRoomName())
                                .hostName(invitation.getRoom().getUser().getNickname())
                                .build())
                .collect(Collectors.toList());

        MyInfo myInfo = MyInfo.builder()
                .nickname(user.getNickname())
                .email(user.getEmail())
                .build();

        MyPageResponse myPageResponse = MyPageResponse.builder()
                .myInfo(myInfo)
                .rooms(hostedRooms)
                .invitations(invitations)
                .build();

        return DataResponseDto.of(myPageResponse);
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60); //1일
        //cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

    private void addRefreshToken(String email, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshToken refreshEntity = RefreshToken.builder()
                .email(email)
                .refresh(refresh)
                .expiration(date.toString())
                .build();

        refreshTokenRepository.save(refreshEntity);
    }
}
