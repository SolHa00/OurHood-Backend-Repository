package server.photo.domain.moment.converter;

import server.photo.domain.comment.entity.Comment;
import server.photo.domain.moment.dto.request.MomentCreateRequest;
import server.photo.domain.moment.dto.response.*;
import server.photo.domain.moment.entity.Moment;
import server.photo.domain.room.entity.Room;
import server.photo.domain.user.entity.User;

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

    public static MomentCreateResponse toMomentCreateResponse(MomentMetadata momentMetadata) {
        return MomentCreateResponse.builder()
                .momentMetadata(momentMetadata)
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

    public static MomentDetailResponse toMomentDetailResponse(MomentDetailMetadata momentMetadata, MomentDetail momentDetail, List<CommentResponse> comments) {
        return MomentDetailResponse.builder()
                .momentMetadata(momentMetadata)
                .momentDetail(momentDetail)
                .comments(comments)
                .build();
    }

    public static MomentMetadata toMomentMetadata(Moment moment) {
        return MomentMetadata.builder()
                .momentId(moment.getId())
                .momentImage(moment.getImageUrl())
                .build();
    }

    public static MomentDetailMetadata toMomentDetailMetadata(Moment moment, User user) {
        return MomentDetailMetadata.builder()
                .momentImage(moment.getImageUrl())
                .userId(user.getId())
                .nickname(user.getNickname())
                .createdAt(moment.getCreatedAt())
                .build();
    }

    public static MomentDetail toMomentDetail(Moment moment) {
        return MomentDetail.builder()
                .momentDescription(moment.getMomentDescription())
                .build();
    }
}
