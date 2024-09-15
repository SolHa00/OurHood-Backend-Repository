package hello.photo.domain.join.controller;

import hello.photo.domain.join.dto.request.JoinRequestCreateDto;
import hello.photo.domain.join.dto.request.JoinRequestHandleDto;
import hello.photo.domain.join.dto.response.JoinRequestListResponse;
import hello.photo.domain.join.service.JoinRequestService;
import hello.photo.global.response.ApiResponse;
import hello.photo.global.response.DataResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/join-requests")
@Tag(name ="Join API", description = "참여 요청 관련 API")
public class JoinRequestController {

    private final JoinRequestService joinRequestService;

    //방 참여 요청
    @PostMapping
    @Operation(summary = "방 참여 요청")
    public ApiResponse joinRequest(@RequestBody JoinRequestCreateDto request) {
        return joinRequestService.createJoinRequest(request);
    }

    //방 참여 요청 목록
    @GetMapping("/{roomId}")
    @Operation(summary = "방 참여 요청 목록")
    public DataResponseDto<JoinRequestListResponse> getJoinRequestList(@PathVariable Long roomId) {
        return joinRequestService.getJoinRequests(roomId);
    }

    //방 참여 요청 처리
    @PostMapping("/{joinRequestId}")
    @Operation(summary = "방 참여 요청 처리")
    public ApiResponse handleJoinRequest(@PathVariable Long joinRequestId, @RequestBody JoinRequestHandleDto request) {
        return joinRequestService.handleJoinRequest(joinRequestId, request);
    }
}
