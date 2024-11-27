package hello.photo.domain.moment.controller;

import hello.photo.domain.moment.dto.request.MomentCreateRequest;
import hello.photo.domain.moment.service.MomentService;
import hello.photo.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/moments")
public class MomentController {

    private final MomentService momentService;

    @PostMapping
    public ApiResponse createMoment(MomentCreateRequest request) {
        return momentService.createMoment(request);
    }

    @GetMapping("/{momentId}")
    public ApiResponse getMoment(@PathVariable Long momentId) {
        return momentService.getMoment(momentId);
    }

    @DeleteMapping("/{momentId}")
    public ApiResponse deleteMoment(@PathVariable Long momentId) {
        return momentService.deleteMoment(momentId);
    }
}
