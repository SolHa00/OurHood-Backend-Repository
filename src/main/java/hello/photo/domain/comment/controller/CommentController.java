package hello.photo.domain.comment.controller;

import hello.photo.domain.comment.dto.request.CommentUpdateRequest;
import hello.photo.domain.comment.dto.request.CommentCreateRequest;
import hello.photo.domain.comment.service.CommentService;
import hello.photo.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    //Comment 생성
    @PostMapping
    public ApiResponse createComment(@RequestBody CommentCreateRequest request) {
        return commentService.createComment(request);
    }

    //Comment 수정
    @PutMapping("/{commentId}")
    public ApiResponse updateContent(@PathVariable Long commentId, @RequestBody CommentUpdateRequest request){
        return commentService.updateComment(commentId, request);
    }

    //Comment 삭제
    @DeleteMapping("/{commentId}")
    public ApiResponse deleteComment(@PathVariable Long commentId) {
        return commentService.deleteComment(commentId);
    }
}
