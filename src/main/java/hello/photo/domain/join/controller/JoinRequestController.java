package hello.photo.domain.join.controller;

import hello.photo.domain.join.dto.request.JoinRequestDto;
import hello.photo.domain.join.dto.response.JoinResponseDto;
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

    @PostMapping
    @Operation(summary = "방 참여 요청")
    public ApiResponse joinRequest(@RequestBody JoinRequestDto request) {
        return joinRequestService.createJoinRequest(request.getRoomId(), request.getUserId());
    }

    @GetMapping("/{roomId}")
    @Operation(summary = "방 참여 요청 목록")
    public DataResponseDto<JoinResponseDto> getJoinRequestList(@PathVariable Long roomId) {
        return joinRequestService.getJoinRequests(roomId);
    }

    //방 참여 요청 처리
    @DeleteMapping("/{joinRequestId}")
    @Operation(summary = "방 참여 요청 처리")
    public ApiResponse handleJoinRequest(@PathVariable Long joinRequestId, @RequestParam String action) {
        return joinRequestService.handleJoinRequest(joinRequestId, action);
    }
}
