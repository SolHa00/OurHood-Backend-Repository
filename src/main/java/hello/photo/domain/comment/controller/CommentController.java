package hello.photo.domain.comment.controller;

import hello.photo.domain.comment.dto.request.CommentCreateRequest;
import hello.photo.domain.comment.service.CommentService;
import hello.photo.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ApiResponse createComment(@RequestBody CommentCreateRequest request) {
        return commentService.createComment(request);
    }
}
