package hello.photo.domain.comment.converter;

import hello.photo.domain.comment.dto.request.CommentCreateRequest;
import hello.photo.domain.comment.dto.response.CommentCreateResponse;
import hello.photo.domain.comment.entity.Comment;
import hello.photo.domain.moment.entity.Moment;
import hello.photo.domain.user.entity.User;

public class CommentConverter {

    public static Comment toComment(CommentCreateRequest request, Moment moment, User user) {
        return Comment.builder()
                .userId(user.getId())
                .moment(moment)
                .content(request.getCommentContent())
                .build();
    }

    public static CommentCreateResponse toCommentCreateResponse(Comment comment) {
        return CommentCreateResponse.builder()
                .commentId(comment.getId())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
