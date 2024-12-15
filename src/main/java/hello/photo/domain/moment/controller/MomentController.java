package hello.photo.domain.moment.controller;

import hello.photo.domain.moment.dto.request.MomentCreateRequest;
import hello.photo.domain.moment.service.MomentService;
import hello.photo.global.handler.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/moments")
public class MomentController {

    private final MomentService momentService;

    @PostMapping
    public BaseResponse createMoment(MomentCreateRequest request) {
        return momentService.createMoment(request);
    }

    @GetMapping("/{momentId}")
    public BaseResponse getMoment(@PathVariable Long momentId) {
        return momentService.getMoment(momentId);
    }

    @DeleteMapping("/{momentId}")
    public BaseResponse deleteMoment(@PathVariable Long momentId) {
        return momentService.deleteMoment(momentId);
    }
}
