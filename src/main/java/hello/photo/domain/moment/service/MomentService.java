package hello.photo.domain.moment.service;

import hello.photo.domain.comment.entity.Comment;
import hello.photo.domain.comment.repository.CommentRepository;
import hello.photo.domain.moment.converter.MomentConverter;
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
import hello.photo.global.handler.BaseException;
import hello.photo.global.handler.response.BaseResponse;
import hello.photo.global.handler.response.BaseResponseStatus;
import hello.photo.global.s3.S3FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MomentService {

    private final MomentRepository momentRepository;
    private final S3FileService s3FileService;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final CommentRepository commentRepository;

    //Moment 생성
    @Transactional
    public BaseResponse createMoment(MomentCreateRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND));
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND));

        String imageUrl = s3FileService.uploadFile(request.getMomentImage());

        Moment moment = MomentConverter.toMoment(imageUrl, request, user, room);
        momentRepository.save(moment);

        MomentCreateResponse momentCreateResponse = MomentConverter.toMomentCreateResponse(moment);

        return BaseResponse.success(momentCreateResponse);
    }

    //특정 Moment 조회
    public BaseResponse getMoment(Long momentId) {
        Moment moment = momentRepository.findById(momentId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND));
        User momentUser = userRepository.findById(moment.getUserId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND));

        String nickname = momentUser.getNickname();
        LocalDateTime createdAt = moment.getCreatedAt();

        List<CommentResponse> comments = new ArrayList<>();
        List<Comment> commentList = commentRepository.findByMoment(moment);
        for (Comment comment : commentList) {
            User commentUser = userRepository.findById(comment.getUserId())
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND));
            CommentResponse commentResponse = MomentConverter.toCommentResponse(comment, commentUser);
            comments.add(commentResponse);
        }

        MomentDetailResponse momentDetailResponse = MomentConverter.toMomentDetailResponse(nickname, moment, createdAt, comments, momentUser);

        return BaseResponse.success(momentDetailResponse);
    }

    //Moment 삭제
    @Transactional
    public BaseResponse deleteMoment(Long momentId) {
        Moment moment = momentRepository.findById(momentId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND));

        String imageUrl = moment.getImageUrl();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            String fileName = extractFileNameFromUrl(imageUrl);
            s3FileService.deleteFile(fileName);
        }

        momentRepository.delete(moment);

        return BaseResponse.success();
    }

    private String extractFileNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

}
