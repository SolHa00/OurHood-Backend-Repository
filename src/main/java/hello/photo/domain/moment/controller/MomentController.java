package hello.photo.domain.moment.controller;

import hello.photo.domain.moment.dto.request.MomentCreateRequest;
import hello.photo.domain.moment.service.MomentService;
import hello.photo.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/moments")
@Tag(name = "Moment API", description = "Moment 생성 및 조회 관련 API")
public class MomentController {

    private final MomentService momentService;

    //roomId도 requestBody로
    @PostMapping
    @Operation(summary = "Moment 생성 API")
    public ApiResponse createMoment(MomentCreateRequest request) {
        return momentService.createMomentObject(request);
    }

}
