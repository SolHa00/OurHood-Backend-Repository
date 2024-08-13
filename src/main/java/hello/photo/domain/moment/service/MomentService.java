package hello.photo.domain.moment.service;

import hello.photo.domain.moment.entity.Moment;
import hello.photo.domain.moment.repository.MomentRepository;
import hello.photo.domain.room.entity.Room;
import hello.photo.domain.room.repository.RoomRepository;
import hello.photo.domain.user.entity.User;
import hello.photo.domain.user.repository.UserRepository;
import hello.photo.global.response.ApiResponse;
import hello.photo.global.s3.S3FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MomentService {

    private final MomentRepository momentRepository;
    private final S3FileService s3FileService;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    public ApiResponse createMomentObject(Long userId, MultipartFile image, String description, Long roomId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("해당 방 없음"));

        String imageUrl = s3FileService.uploadFile(image);

        Moment moment = new Moment();
        moment.setImageUrl(imageUrl);
        moment.setMomentDescription(description);
        moment.setUser(user);
        moment.setRoom(room);
        momentRepository.save(moment);

        return ApiResponse.of(imageUrl);
    }
}
