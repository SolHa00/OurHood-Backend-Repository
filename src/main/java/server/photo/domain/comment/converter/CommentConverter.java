package server.photo.domain.comment.converter;

import server.photo.domain.comment.dto.request.CommentCreateRequest;
import server.photo.domain.comment.dto.response.CommentCreateResponse;
import server.photo.domain.comment.entity.Comment;
import server.photo.domain.moment.entity.Moment;
import server.photo.domain.user.entity.User;

public class CommentConverter {

    public static Comment toComment(CommentCreateRequest request, Moment moment, User user) {
        return Comment.builder()
                .user(user)
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
