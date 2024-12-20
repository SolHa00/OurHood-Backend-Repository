package hello.photo.domain.user.service;

import hello.photo.domain.invitation.dto.response.InvitationInfo;
import hello.photo.domain.invitation.entity.Invitation;
import hello.photo.domain.invitation.repository.InvitationRepository;
import hello.photo.domain.refresh.service.JwtTokenService;
import hello.photo.domain.refresh.service.RefreshTokenService;
import hello.photo.domain.room.converter.RoomConverter;
import hello.photo.domain.room.entity.Room;
import hello.photo.domain.room.entity.RoomMembers;
import hello.photo.domain.user.converter.UserConverter;
import hello.photo.domain.user.dto.request.UserLoginRequest;
import hello.photo.domain.user.dto.request.UserSignUpRequest;
import hello.photo.domain.user.dto.response.*;
import hello.photo.domain.user.entity.User;
import hello.photo.domain.user.repository.UserRepository;
import hello.photo.global.handler.BaseException;
import hello.photo.global.handler.response.BaseResponse;
import hello.photo.global.handler.response.BaseResponseStatus;
import hello.photo.global.util.CookieUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService customUserDetailsService;
    private final InvitationRepository invitationRepository;
    private final RefreshTokenService refreshTokenService;
    private final JwtTokenService jwtTokenService;


    @Transactional
    public BaseResponse signup(UserSignUpRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BaseException(BaseResponseStatus.USER_EMAIL_DUPLICATED);
        }
        if (userRepository.existsByNickname(request.getNickname())) {
            throw new BaseException(BaseResponseStatus.USER_NICKNAME_DUPLICATED);
        }

        String password = passwordEncoder.encode(request.getPassword());
        User user = UserConverter.toUser(request, password);

        userRepository.save(user);

        return BaseResponse.success();
    }

    public BaseResponse login(UserLoginRequest request, HttpServletResponse response) {
        UserDetails userData = customUserDetailsService.loadUserByUsername(request.getEmail());
        if (!passwordEncoder.matches(request.getPassword(), userData.getPassword())) {
            throw new BaseException(BaseResponseStatus.LOGIN_FAIL);
        }

        User user = userRepository.findByEmail(request.getEmail());

        String accessToken = jwtTokenService.createAccessToken(user.getEmail()); // 2시간
        String refreshToken = jwtTokenService.createRefreshToken(user.getEmail()); // 7일
        refreshTokenService.saveRefreshToken(user.getEmail(), refreshToken, 1000 * 60 * 60 * 24 *7L); // 7일

        response.setHeader("accessToken", accessToken);
        response.addCookie(CookieUtil.createCookie("refreshToken", refreshToken));

        UserLoginInfo userLoginInfo = UserConverter.toUserLoginInfo(user);
        UserLoginResponse userLoginResponse = UserConverter.toUserLoginResponse(userLoginInfo);

        return BaseResponse.success(userLoginResponse);
    }

    public BaseResponse<MyPageResponse> getMyPage(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND));

        MyInfo myInfo = UserConverter.toMyInfo(user);

        List<Invitation> invitationList = invitationRepository.findByUserId(user.getId());
        List<InvitationInfo> invitations = new ArrayList<>();

        for (Invitation invitation : invitationList) {
            User host = userRepository.findById(invitation.getRoom().getUserId())
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND));
            InvitationInfo invitationInfo = UserConverter.toInvitaionInfo(invitation, host);
            invitations.add(invitationInfo);
        }

        List<RoomsMyPageInfo> hostedRooms = new ArrayList<>();
        List<RoomMembers> roomMembersList = user.getRooms();

        for (RoomMembers roomMembers : roomMembersList) {
            Room room = roomMembers.getRoom();
            User host = userRepository.findById(room.getUserId())
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND));

            RoomsMyPageInfo roomsMyPageInfo = UserConverter.toRoomsMyPageInfo(room, host);

            hostedRooms.add(roomsMyPageInfo);
        }

        MyPageResponse myPageResponse = RoomConverter.toMyPageResponse(myInfo, hostedRooms, invitations);

        return BaseResponse.success(myPageResponse);
    }
}
