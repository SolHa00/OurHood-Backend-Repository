package server.photo.domain.user.application;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.photo.domain.invitation.dto.response.InvitationInfo;
import server.photo.domain.invitation.entity.Invitation;
import server.photo.domain.invitation.repository.InvitationRepository;
import server.photo.domain.refresh.application.JwtTokenService;
import server.photo.domain.refresh.application.RefreshTokenService;
import server.photo.domain.room.converter.RoomConverter;
import server.photo.domain.room.entity.Room;
import server.photo.domain.room.entity.RoomMembers;
import server.photo.domain.user.converter.UserConverter;
import server.photo.domain.user.dto.request.UserLoginRequest;
import server.photo.domain.user.dto.request.UserSignUpRequest;
import server.photo.domain.user.dto.response.*;
import server.photo.domain.user.entity.User;
import server.photo.domain.user.repository.UserRepository;
import server.photo.global.handler.response.BaseException;
import server.photo.global.handler.response.BaseResponse;
import server.photo.global.handler.response.BaseResponseStatus;
import server.photo.global.util.CookieUtil;

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
    public BaseResponse<Object> signup(UserSignUpRequest request) {

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

    public BaseResponse<UserLoginResponse> login(UserLoginRequest request, HttpServletResponse response) {
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
                .orElseThrow(() -> new BaseException(BaseResponseStatus.USER_NOT_FOUND));

        List<Invitation> invitationList = invitationRepository.findByUserId(user.getId());
        List<InvitationInfo> invitations = new ArrayList<>();

        for (Invitation invitation : invitationList) {
            User host = userRepository.findById(invitation.getRoom().getUserId())
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.USER_NOT_FOUND));
            Room room = invitation.getRoom();
            InvitationMetadata invitationMetadata = UserConverter.toInvitationMetadata(invitation);
            InvitingRoomInfo invitingRoomInfo = UserConverter.toInvitingRoomInfo(room, host);
            InvitationInfo invitationInfo = UserConverter.toInvitationInfo(invitationMetadata, invitingRoomInfo);
            invitations.add(invitationInfo);
        }

        List<RoomMembers> roomMembersList = user.getRooms();
        List<RoomInfo> myRooms = new ArrayList<>();

        for (RoomMembers roomMembers : roomMembersList) {
            Room room = roomMembers.getRoom();
            User host = userRepository.findById(room.getUserId())
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.USER_NOT_FOUND));
            RoomMetadata roomMetadata = UserConverter.toRoomMetadata(room, host);
            RoomDetail roomDetail = UserConverter.toRoomDetail(room);
            RoomInfo roomInfo = UserConverter.toRoomInfo(roomMetadata, roomDetail);
            myRooms.add(roomInfo);
        }

        MyPageResponse myPageResponse = RoomConverter.toMyPageResponse(myRooms, invitations);

        return BaseResponse.success(myPageResponse);
    }
}
