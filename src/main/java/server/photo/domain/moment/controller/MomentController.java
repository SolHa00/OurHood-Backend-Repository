package server.photo.domain.moment.controller;

import server.photo.domain.moment.dto.request.MomentCreateRequest;
import server.photo.domain.moment.dto.request.MomentDescriptionRequest;
import server.photo.domain.moment.dto.response.MomentCreateResponse;
import server.photo.domain.moment.dto.response.MomentDetailResponse;
import server.photo.domain.moment.service.MomentService;
import server.photo.global.handler.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/moments")
public class MomentController {

    private final MomentService momentService;

    //Moment 생성
    @PostMapping
    public BaseResponse<MomentCreateResponse> createMoment(MomentCreateRequest request) {
        return momentService.createMoment(request);
    }

    //특정 Moment 조회
    @GetMapping("/{momentId}")
    public BaseResponse<MomentDetailResponse> getMoment(@PathVariable Long momentId) {
        return momentService.getMoment(momentId);
    }

    @DeleteMapping("/{momentId}")
    public BaseResponse<Object> deleteMoment(@PathVariable Long momentId) {
        return momentService.deleteMoment(momentId);
    }

    @PutMapping("/{momentId}")
    public BaseResponse<Object> changeMomentDescription(@PathVariable Long momentId, @RequestBody MomentDescriptionRequest request) {
        return momentService.changeMomentDescription(momentId, request);
    }
}
