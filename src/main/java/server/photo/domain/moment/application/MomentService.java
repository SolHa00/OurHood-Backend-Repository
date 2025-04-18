package server.photo.domain.moment.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.photo.domain.comment.entity.Comment;
import server.photo.domain.comment.repository.CommentRepository;
import server.photo.domain.moment.converter.MomentConverter;
import server.photo.domain.moment.dto.request.MomentCreateRequest;
import server.photo.domain.moment.dto.request.MomentDescriptionRequest;
import server.photo.domain.moment.dto.response.*;
import server.photo.domain.moment.entity.Moment;
import server.photo.domain.moment.repository.MomentRepository;
import server.photo.domain.room.entity.Room;
import server.photo.domain.room.repository.RoomRepository;
import server.photo.domain.user.entity.User;
import server.photo.domain.user.repository.UserRepository;
import server.photo.global.handler.response.BaseException;
import server.photo.global.handler.response.BaseResponse;
import server.photo.global.handler.response.BaseResponseStatus;
import server.photo.global.s3.S3FileService;

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
    public BaseResponse<MomentCreateResponse> createMoment(MomentCreateRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_USER));
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_ROOM));

        String imageUrl = s3FileService.uploadFile(request.getMomentImage());

        Moment moment = MomentConverter.toMoment(imageUrl, request, user, room);
        momentRepository.save(moment);

        MomentCreateResponse momentCreateResponse = MomentConverter.toMomentCreateResponse(moment);

        return BaseResponse.success(momentCreateResponse);
    }

    //특정 Moment 조회
    public BaseResponse<MomentDetailResponse> getMoment(Long momentId) {
        Moment moment = momentRepository.findById(momentId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_MOMENT));
        User momentUser = userRepository.findById(moment.getUserId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_USER));

        MomentDetailMetadata momentMetadata = MomentConverter.toMomentDetailMetadata(moment, momentUser);
        MomentDetail momentDetail = MomentConverter.toMomentDetail(moment);

        List<CommentResponse> comments = new ArrayList<>();
        List<Comment> commentList = commentRepository.findByMoment(moment);
        for (Comment comment : commentList) {
            User commentUser = comment.getUser();
            CommentResponse commentResponse = MomentConverter.toCommentResponse(comment, commentUser);
            comments.add(commentResponse);
        }

        MomentDetailResponse momentDetailResponse = MomentConverter.toMomentDetailResponse(momentMetadata, momentDetail, comments);

        return BaseResponse.success(momentDetailResponse);
    }

    //Moment 삭제
    @Transactional
    public BaseResponse<Object> deleteMoment(Long momentId) {
        Moment moment = momentRepository.findById(momentId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_MOMENT));

        String imageUrl = moment.getImageUrl();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            String fileName = extractFileNameFromUrl(imageUrl);
            s3FileService.deleteFile(fileName);
        }

        momentRepository.delete(moment);

        return BaseResponse.success();
    }

    @Transactional
    public BaseResponse<Object> changeMomentDescription(Long momentId, MomentDescriptionRequest request) {
        Moment moment = momentRepository.findById(momentId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_MOMENT));
        moment.updateMomentDescription(request.getMomentDescription());
        return BaseResponse.success();
    }

    private String extractFileNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

}
