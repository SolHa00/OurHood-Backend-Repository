package hello.photo.domain.comment.service;

import hello.photo.domain.comment.dto.request.CommentCreateRequest;
import hello.photo.domain.comment.dto.request.CommentUpdateRequest;
import hello.photo.domain.comment.dto.response.CommentCreateResponse;
import hello.photo.domain.comment.entity.Comment;
import hello.photo.domain.comment.repository.CommentRepository;
import hello.photo.domain.moment.entity.Moment;
import hello.photo.domain.moment.repository.MomentRepository;
import hello.photo.domain.user.entity.User;
import hello.photo.domain.user.repository.UserRepository;
import hello.photo.global.exception.EntityNotFoundException;
import hello.photo.global.response.ApiResponse;
import hello.photo.global.response.Code;
import hello.photo.global.response.DataResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MomentRepository momentRepository;
    private final UserRepository userRepository;

    //Comment 생성
    @Transactional
    public ApiResponse createComment(CommentCreateRequest request) {
        Moment moment = momentRepository.findById(request.getMomentId())
                .orElseThrow(() -> new EntityNotFoundException(Code.NOT_FOUND, Code.NOT_FOUND.getMessage()));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(Code.NOT_FOUND, Code.NOT_FOUND.getMessage()));

        Comment comment = Comment.builder()
                .content(request.getCommentContent())
                .user(user)
                .moment(moment)
                .build();

        commentRepository.save(comment);

        CommentCreateResponse response = CommentCreateResponse.builder()
                .commentId(comment.getId())
                .createdAt(comment.getCreatedAt())
                .build();
        return DataResponseDto.of(response);
    }

    @Transactional
    public ApiResponse updateComment(Long commentId, CommentUpdateRequest request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException(Code.NOT_FOUND, Code.NOT_FOUND.getMessage()));

        comment.updateContent(request.getCommentContent());

        return ApiResponse.of(Code.OK.getMessage());
    }

    //Comment 삭제
    @Transactional
    public ApiResponse deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException(Code.NOT_FOUND, Code.NOT_FOUND.getMessage()));
        commentRepository.delete(comment);
        return ApiResponse.of(Code.OK.getMessage());
    }
}
