package server.photo.domain.comment.application;

import server.photo.domain.comment.converter.CommentConverter;
import server.photo.domain.comment.dto.request.CommentCreateRequest;
import server.photo.domain.comment.dto.request.CommentUpdateRequest;
import server.photo.domain.comment.dto.response.CommentCreateResponse;
import server.photo.domain.comment.entity.Comment;
import server.photo.domain.comment.repository.CommentRepository;
import server.photo.domain.moment.entity.Moment;
import server.photo.domain.moment.repository.MomentRepository;
import server.photo.domain.user.entity.User;
import server.photo.domain.user.repository.UserRepository;
import server.photo.global.handler.response.BaseException;
import server.photo.global.handler.response.BaseResponse;
import server.photo.global.handler.response.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MomentRepository momentRepository;
    private final UserRepository userRepository;

    @Transactional
    public BaseResponse<CommentCreateResponse> createComment(CommentCreateRequest request) {
        Moment moment = momentRepository.findById(request.getMomentId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.MOMENT_NOT_FOUND));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.USER_NOT_FOUND));

        Comment comment = CommentConverter.toComment(request, moment, user);
        commentRepository.save(comment);

        CommentCreateResponse response = CommentConverter.toCommentCreateResponse(comment);
        return BaseResponse.success(response);
    }

    @Transactional
    public BaseResponse<Object> updateComment(Long commentId, CommentUpdateRequest request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.COMMENT_NOT_FOUND));
        comment.updateContent(request.getCommentContent());
        return BaseResponse.success();
    }

    @Transactional
    public BaseResponse<Object> deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.COMMENT_NOT_FOUND));
        commentRepository.delete(comment);
        return BaseResponse.success();
    }
}
