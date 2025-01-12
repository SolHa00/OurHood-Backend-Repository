package server.photo.domain.moment.service;

import server.photo.domain.comment.entity.Comment;
import server.photo.domain.comment.repository.CommentRepository;
import server.photo.domain.moment.converter.MomentConverter;
import server.photo.domain.moment.dto.request.MomentCreateRequest;
import server.photo.domain.moment.dto.request.MomentDescriptionRequest;
import server.photo.domain.moment.dto.response.CommentResponse;
import server.photo.domain.moment.dto.response.MomentCreateResponse;
import server.photo.domain.moment.dto.response.MomentDetailResponse;
import server.photo.domain.moment.entity.Moment;
import server.photo.domain.moment.repository.MomentRepository;
import server.photo.domain.room.entity.Room;
import server.photo.domain.room.repository.RoomRepository;
import server.photo.domain.user.entity.User;
import server.photo.domain.user.repository.UserRepository;
import server.photo.global.handler.BaseException;
import server.photo.global.handler.response.BaseResponse;
import server.photo.global.handler.response.BaseResponseStatus;
import server.photo.global.s3.S3FileService;
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
                .orElseThrow(() -> new BaseException(BaseResponseStatus.USER_NOT_FOUND));
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.ROOM_NOT_FOUND));

        String imageUrl = s3FileService.uploadFile(request.getMomentImage());

        Moment moment = MomentConverter.toMoment(imageUrl, request, user, room);
        momentRepository.save(moment);

        MomentCreateResponse momentCreateResponse = MomentConverter.toMomentCreateResponse(moment);

        return BaseResponse.success(momentCreateResponse);
    }

    //특정 Moment 조회
    public BaseResponse getMoment(Long momentId) {
        Moment moment = momentRepository.findById(momentId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.MOMENT_NOT_FOUND));
        User momentUser = userRepository.findById(moment.getUserId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.USER_NOT_FOUND));

        String nickname = momentUser.getNickname();
        LocalDateTime createdAt = moment.getCreatedAt();

        List<CommentResponse> comments = new ArrayList<>();
        List<Comment> commentList = commentRepository.findByMoment(moment);
        for (Comment comment : commentList) {
            User commentUser = userRepository.findById(comment.getUserId())
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.USER_NOT_FOUND));
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
                .orElseThrow(() -> new BaseException(BaseResponseStatus.MOMENT_NOT_FOUND));

        String imageUrl = moment.getImageUrl();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            String fileName = extractFileNameFromUrl(imageUrl);
            s3FileService.deleteFile(fileName);
        }

        momentRepository.delete(moment);

        return BaseResponse.success();
    }

    @Transactional
    public BaseResponse changeMomentDescription(Long momentId, MomentDescriptionRequest request) {
        Moment moment = momentRepository.findById(momentId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.MOMENT_NOT_FOUND));
        moment.updateMomentDescription(request.getMomentDescription());
        return BaseResponse.success();
    }

    private String extractFileNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

}
