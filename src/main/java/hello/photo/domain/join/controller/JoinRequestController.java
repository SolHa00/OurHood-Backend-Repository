package hello.photo.domain.join.controller;

import hello.photo.domain.join.dto.request.JoinRequestCreateDto;
import hello.photo.domain.join.dto.request.JoinRequestHandleDto;
import hello.photo.domain.join.dto.response.JoinRequestListResponse;
import hello.photo.domain.join.service.JoinRequestService;
import hello.photo.global.handler.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/join-requests")
public class JoinRequestController {

    private final JoinRequestService joinRequestService;

    //방 참여 요청 생성
    @PostMapping
    public BaseResponse joinRequest(@RequestBody JoinRequestCreateDto request) {
        return joinRequestService.createJoinRequest(request);
    }

    //방 참여 요청 목록
    @GetMapping("/{roomId}")
    public BaseResponse<JoinRequestListResponse> getJoinRequestList(@PathVariable Long roomId) {
        return joinRequestService.getJoinRequests(roomId);
    }

    //방 참여 요청 처리
    @PostMapping("/{joinRequestId}")
    public BaseResponse handleJoinRequest(@PathVariable Long joinRequestId, @RequestBody JoinRequestHandleDto request) {
        return joinRequestService.handleJoinRequest(joinRequestId, request);
    }
}
