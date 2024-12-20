package hello.photo.domain.comment.service;

import hello.photo.domain.comment.converter.CommentConverter;
import hello.photo.domain.comment.dto.request.CommentCreateRequest;
import hello.photo.domain.comment.dto.request.CommentUpdateRequest;
import hello.photo.domain.comment.dto.response.CommentCreateResponse;
import hello.photo.domain.comment.entity.Comment;
import hello.photo.domain.comment.repository.CommentRepository;
import hello.photo.domain.moment.entity.Moment;
import hello.photo.domain.moment.repository.MomentRepository;
import hello.photo.domain.user.entity.User;
import hello.photo.domain.user.repository.UserRepository;
import hello.photo.global.handler.BaseException;
import hello.photo.global.handler.response.BaseResponse;
import hello.photo.global.handler.response.BaseResponseStatus;
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
    public BaseResponse createComment(CommentCreateRequest request) {
        Moment moment = momentRepository.findById(request.getMomentId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND));

        Comment comment = CommentConverter.toComment(request, moment, user);
        commentRepository.save(comment);

        CommentCreateResponse response = CommentConverter.toCommentCreateResponse(comment);
        return BaseResponse.success(response);
    }

    @Transactional
    public BaseResponse updateComment(Long commentId, CommentUpdateRequest request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND));
        comment.updateContent(request.getCommentContent());
        return BaseResponse.success();
    }

    @Transactional
    public BaseResponse deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND));
        commentRepository.delete(comment);
        return BaseResponse.success();
    }
}
