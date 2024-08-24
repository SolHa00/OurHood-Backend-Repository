package hello.photo.domain.moment.service;

import hello.photo.domain.moment.dto.request.MomentCreateRequest;
import hello.photo.domain.moment.dto.response.MomentCreateResponse;
import hello.photo.domain.moment.entity.Moment;
import hello.photo.domain.moment.repository.MomentRepository;
import hello.photo.domain.room.entity.Room;
import hello.photo.domain.room.repository.RoomRepository;
import hello.photo.domain.user.entity.User;
import hello.photo.domain.user.repository.UserRepository;
import hello.photo.global.exception.EntityNotFoundException;
import hello.photo.global.response.ApiResponse;
import hello.photo.global.response.DataResponseDto;
import hello.photo.global.s3.S3FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MomentService {

    private final MomentRepository momentRepository;
    private final S3FileService s3FileService;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    public ApiResponse createMomentObject(MomentCreateRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("해당 회원을 찾을 수 없습니다"));
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new EntityNotFoundException("해당 Room을 찾을 수 없습니다"));

        String imageUrl = s3FileService.uploadFile(request.getMomentImage());

        Moment moment = new Moment();
        moment.setImageUrl(imageUrl);
        moment.setMomentDescription(request.getMomentDescription());
        moment.setUser(user);
        moment.setRoom(room);
        momentRepository.save(moment);

        MomentCreateResponse momentCreateResponse = new MomentCreateResponse(moment.getId(), imageUrl);

        return DataResponseDto.of(momentCreateResponse);
    }
}
