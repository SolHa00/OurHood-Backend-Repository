package hello.photo.domain.moment.converter;

import hello.photo.domain.comment.entity.Comment;
import hello.photo.domain.moment.dto.request.MomentCreateRequest;
import hello.photo.domain.moment.dto.response.CommentResponse;
import hello.photo.domain.moment.dto.response.MomentCreateResponse;
import hello.photo.domain.moment.dto.response.MomentDetailResponse;
import hello.photo.domain.moment.entity.Moment;
import hello.photo.domain.room.entity.Room;
import hello.photo.domain.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public class MomentConverter {

    public static Moment toMoment(String imageUrl, MomentCreateRequest request, User user, Room room) {
        return Moment.builder()
                .imageUrl(imageUrl)
                .momentDescription(request.getMomentDescription())
                .userId(user.getId())
                .room(room)
                .build();
    }

    public static MomentCreateResponse toMomentCreateResponse(Moment moment) {
        return MomentCreateResponse.builder()
                .momentId(moment.getId())
                .build();
    }

    public static CommentResponse toCommentResponse(Comment comment, User commentUser) {
        return CommentResponse.builder()
                .commentId(comment.getId())
                .nickname(commentUser.getNickname())
                .commentContent(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .userId(commentUser.getId())
                .build();
    }

    public static MomentDetailResponse toMomentDetailResponse(String nickname, Moment moment, LocalDateTime createdAt, List<CommentResponse> comments, User momentUser) {
        return MomentDetailResponse.builder()
                .nickname(nickname)
                .momentImage(moment.getImageUrl())
                .momentDescription(moment.getMomentDescription())
                .createdAt(createdAt)
                .comments(comments)
                .userId(momentUser.getId())
                .build();
    }

}
