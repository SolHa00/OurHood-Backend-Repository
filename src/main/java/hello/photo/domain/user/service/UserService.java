package hello.photo.domain.user.service;

import hello.photo.domain.invitation.dto.response.InvitationInfo;
import hello.photo.domain.invitation.entity.Invitation;
import hello.photo.domain.invitation.repository.InvitationRepository;
import hello.photo.domain.refresh.entity.RefreshToken;
import hello.photo.domain.refresh.repository.RefreshTokenRepository;
import hello.photo.domain.room.converter.RoomConverter;
import hello.photo.domain.room.entity.Room;
import hello.photo.domain.room.entity.RoomMembers;
import hello.photo.domain.user.converter.UserConverter;
import hello.photo.domain.user.dto.request.UserLoginRequest;
import hello.photo.domain.user.dto.request.UserSignUpRequest;
import hello.photo.domain.user.dto.response.*;
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

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    public ApiResponse signup(UserSignUpRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateException(Code.USER_EMAIL_DUPLICATED, Code.USER_EMAIL_DUPLICATED.getMessage());
        }
        if (userRepository.existsByNickname(request.getNickname())) {
            throw new DuplicateException(Code.USER_NICKNAME_DUPLICATED, Code.USER_NICKNAME_DUPLICATED.getMessage());
        }

        String password = passwordEncoder.encode(request.getPassword());
        User user = UserConverter.toUser(request, password);

        userRepository.save(user);

        return ApiResponse.of(Code.OK.getMessage());
    }

    public ApiResponse login(UserLoginRequest request, HttpServletResponse response) {
        UserDetails userData = customUserDetailsService.loadUserByUsername(request.getEmail());
        if (!passwordEncoder.matches(request.getPassword(), userData.getPassword())) {
            throw new LogInFailException(Code.LOGIN_FAIL, Code.LOGIN_FAIL.getMessage());
        }

        User user = userRepository.findByEmail(request.getEmail());

        // 토큰 생성
        String accessToken = jwtUtil.createJwt("accessToken", user.getEmail(), 1000 * 60 * 60 *2L); // 2시간
        String refreshToken = jwtUtil.createJwt("refreshToken", user.getEmail(), 1000 * 60 * 60 * 24 *7L); // 7일
        addRefreshToken(user.getEmail(), refreshToken, 1000 * 60 * 60 * 24 *7L); // 7일

        // 응답 생성
        response.setHeader("accessToken", accessToken);
        response.addCookie(createCookie("refreshToken", refreshToken));

        UserLoginInfo userLoginInfo = UserConverter.toUserLoginInfo(user);
        UserLoginResponse userLoginResponse = UserConverter.toUserLoginResponse(userLoginInfo);

        return DataResponseDto.of(userLoginResponse, Code.OK.getMessage());

    }

    public DataResponseDto<MyPageResponse> getMyPage(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(Code.NOT_FOUND, Code.NOT_FOUND.getMessage()));

        MyInfo myInfo = UserConverter.toMyInfo(user);

        List<Invitation> invitationList = invitationRepository.findByUserId(user.getId());
        List<InvitationInfo> invitations = new ArrayList<>();

        for (Invitation invitation : invitationList) {
            User host = userRepository.findById(invitation.getRoom().getUserId())
                    .orElseThrow(() -> new EntityNotFoundException(Code.NOT_FOUND, Code.NOT_FOUND.getMessage()));
            InvitationInfo invitationInfo = UserConverter.toInvitaionInfo(invitation, host);
            invitations.add(invitationInfo);
        }

        List<RoomsMyPageInfo> hostedRooms = new ArrayList<>();
        List<RoomMembers> roomMembersList = user.getRooms();

        for (RoomMembers roomMembers : roomMembersList) {
            Room room = roomMembers.getRoom();
            User host = userRepository.findById(room.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException(Code.NOT_FOUND, Code.NOT_FOUND.getMessage()));

            RoomsMyPageInfo roomsMyPageInfo = UserConverter.toRoomsMyPageInfo(room, host);

            hostedRooms.add(roomsMyPageInfo);
        }

        MyPageResponse myPageResponse = RoomConverter.toMyPageResponse(myInfo, hostedRooms, invitations);

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

        LocalDateTime expirationDate = LocalDateTime.now().plus(Duration.ofMillis(expiredMs));

        RefreshToken refreshEntity = RefreshToken.builder()
                .email(email)
                .refresh(refresh)
                .expiration(expirationDate)
                .build();

        refreshTokenRepository.save(refreshEntity);
    }
}
