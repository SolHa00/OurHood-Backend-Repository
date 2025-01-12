package server.photo.domain.join.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import server.photo.domain.join.dto.request.JoinRequestCreateDto;
import server.photo.domain.join.dto.request.JoinRequestHandleDto;
import server.photo.domain.join.service.JoinRequestService;
import server.photo.global.handler.response.BaseResponse;

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

    //방 참여 요청 처리
    @PostMapping("/{joinRequestId}")
    public BaseResponse handleJoinRequest(@PathVariable Long joinRequestId, @RequestBody JoinRequestHandleDto request) {
        return joinRequestService.handleJoinRequest(joinRequestId, request);
    }

    //방 참여 요청 삭제
    @DeleteMapping("/{joinRequestId}")
    public BaseResponse deleteJoinRequest(@PathVariable Long joinRequestId) {
        return joinRequestService.deleteJoinRequest(joinRequestId);
    }
}
