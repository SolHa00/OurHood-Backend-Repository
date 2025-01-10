package server.photo.domain.comment.controller;

import server.photo.domain.comment.dto.request.CommentUpdateRequest;
import server.photo.domain.comment.dto.request.CommentCreateRequest;
import server.photo.domain.comment.service.CommentService;
import server.photo.global.handler.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    //Comment 생성
    @PostMapping
    public BaseResponse createComment(@RequestBody CommentCreateRequest request) {
        return commentService.createComment(request);
    }

    //Comment 수정
    @PutMapping("/{commentId}")
    public BaseResponse updateContent(@PathVariable Long commentId, @RequestBody CommentUpdateRequest request){
        return commentService.updateComment(commentId, request);
    }

    //Comment 삭제
    @DeleteMapping("/{commentId}")
    public BaseResponse deleteComment(@PathVariable Long commentId) {
        return commentService.deleteComment(commentId);
    }
}
