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
import server.photo.domain.join.entity.JoinRequest;
import server.photo.domain.join.repository.JoinRequestRepository;
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
    private final JoinRequestRepository joinRequestRepository;


    @Transactional
    public BaseResponse<Object> signup(UserSignUpRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BaseException(BaseResponseStatus.DUPLICATE_EMAIL);
        }
        if (userRepository.existsByNickname(request.getNickname())) {
            throw new BaseException(BaseResponseStatus.DUPLICATE_NICKNAME);
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
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_USER));

        List<RoomMembers> roomMembersList = user.getRooms();
        List<RoomInfo> roomInfoList = new ArrayList<>();

        for (RoomMembers roomMembers : roomMembersList) {
            Room room = roomMembers.getRoom();
            User host = userRepository.findById(room.getUserId())
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_USER));
            RoomMetadata roomMetadata = UserConverter.toRoomMetadata(room, host);
            RoomDetail roomDetail = UserConverter.toRoomDetail(room);
            RoomInfo roomInfo = UserConverter.toRoomInfo(roomMetadata, roomDetail);
            roomInfoList.add(roomInfo);
        }

        List<Invitation> invitations = invitationRepository.findByUserId(userId);
        List<InvitationInfo> invitationInfoList = new ArrayList<>();

        for (Invitation invitation : invitations) {
            User host = userRepository.findById(invitation.getRoom().getUserId())
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_USER));
            Room room = invitation.getRoom();
            InvitationInfo invitationInfo = UserConverter.toInvitationInfo(invitation, room, host);
            invitationInfoList.add(invitationInfo);
        }

        List<JoinRequest> joinRequests = joinRequestRepository.findByUserId(userId);
        List<JoinRequestInfo> joinRequestInfoList = new ArrayList<>();

        for(JoinRequest joinRequest : joinRequests) {
            Room room = joinRequest.getRoom();
            JoinRequestInfo joinRequestInfo = UserConverter.toJoinRequestInfo(joinRequest, room);
            joinRequestInfoList.add(joinRequestInfo);
        }

        MyPageResponse myPageResponse = RoomConverter.toMyPageResponse(roomInfoList, invitationInfoList, joinRequestInfoList);

        return BaseResponse.success(myPageResponse);
    }
}
