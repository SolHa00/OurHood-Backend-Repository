package hello.photo.domain.user.service;

import hello.photo.domain.invitation.dto.InvitationInfo;
import hello.photo.domain.invitation.repository.InvitationRepository;
import hello.photo.domain.room.dto.RoomInfo;
import hello.photo.domain.user.dto.request.UserLoginRequest;
import hello.photo.domain.user.dto.request.UserSignupRequest;
import hello.photo.domain.user.dto.response.MyPageResponse;
import hello.photo.domain.user.dto.response.UserLoginResponse;
import hello.photo.domain.user.entity.User;
import hello.photo.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public void signup(UserSignupRequest request) {
        User user = User.builder()
                .nickname(request.getNickname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        userRepository.save(user);
    }



    public UserLoginResponse login(UserLoginRequest request) {
        UserDetails userData = customUserDetailsService.loadUserByUsername(request.getEmail());
        Optional<User> user = userRepository.findByEmail(request.getEmail());

        if (!passwordEncoder.matches(request.getPassword(), userData.getPassword())) {
            throw new RuntimeException("이메일이나 비밀번호가 틀렸습니다.");
        }

        return new UserLoginResponse(user.get().getId(), user.get().getNickname());
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
