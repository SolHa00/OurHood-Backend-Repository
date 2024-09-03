package hello.photo.domain.moment.service;

import hello.photo.domain.comment.repository.CommentRepository;
import hello.photo.domain.moment.dto.request.MomentCreateRequest;
import hello.photo.domain.moment.dto.response.CommentResponse;
import hello.photo.domain.moment.dto.response.MomentCreateResponse;
import hello.photo.domain.moment.dto.response.MomentDetailResponse;
import hello.photo.domain.moment.entity.Moment;
import hello.photo.domain.moment.repository.MomentRepository;
import hello.photo.domain.room.entity.Room;
import hello.photo.domain.room.repository.RoomRepository;
import hello.photo.domain.user.entity.User;
import hello.photo.domain.user.repository.UserRepository;
import hello.photo.global.exception.EntityNotFoundException;
import hello.photo.global.response.ApiResponse;
import hello.photo.global.response.Code;
import hello.photo.global.response.DataResponseDto;
import hello.photo.global.s3.S3FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MomentService {

    private final MomentRepository momentRepository;
    private final S3FileService s3FileService;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final CommentRepository commentRepository;

    //Moment 생성
    public ApiResponse createMomentObject(MomentCreateRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(Code.NOT_FOUND, Code.NOT_FOUND.getMessage()));
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new EntityNotFoundException(Code.NOT_FOUND, Code.NOT_FOUND.getMessage()));

        String imageUrl = s3FileService.uploadFile(request.getMomentImage());

        Moment moment = Moment.builder()
                .imageUrl(imageUrl)
                .momentDescription(request.getMomentDescription())
                .user(user)
                .room(room)
                .build();
        momentRepository.save(moment);

        MomentCreateResponse momentCreateResponse = MomentCreateResponse.builder()
                .momentId(moment.getId())
                .imageUrl(imageUrl).build();

        return DataResponseDto.of(momentCreateResponse);
    }

    //특정 Moment 조회
    public ApiResponse getMomentObject(Long momentId) {
        Moment moment = momentRepository.findById(momentId)
                .orElseThrow(() -> new EntityNotFoundException(Code.NOT_FOUND, Code.NOT_FOUND.getMessage()));

        String nickname = moment.getUser().getNickname();
        OffsetDateTime createdAt = moment.getCreatedAt();
        List<CommentResponse> comments = commentRepository.findByMoment(moment).stream()
                .map(comment -> CommentResponse.builder()
                                .commentId(comment.getId())
                                .nickname(comment.getUser().getNickname())
                                .commentContent(comment.getContent())
                                .createdAt(comment.getCreatedAt())
                                .build())
                .collect(Collectors.toList());

        MomentDetailResponse momentDetailResponse = MomentDetailResponse.builder()
                .nickname(nickname)
                .momentImage(moment.getImageUrl())
                .momentDescription(moment.getMomentDescription())
                .createdAt(createdAt)
                .comments(comments)
                .build();

        return DataResponseDto.of(momentDetailResponse, Code.OK.getMessage());
    }
}
