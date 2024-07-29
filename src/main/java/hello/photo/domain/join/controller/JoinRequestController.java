package hello.photo.domain.join.controller;

import hello.photo.domain.join.service.JoinRequestService;
import hello.photo.domain.join.dto.request.JoinRequestDto;
import hello.photo.domain.join.dto.response.JoinResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name ="Join API", description = "참여 요청 관련 API")
public class JoinRequestController {

    private final JoinRequestService joinRequestService;

    @PostMapping("/join-requests")
    @Operation(summary = "방 참여 요청 API")
    public void joinRequest(@RequestBody JoinRequestDto request) {
        joinRequestService.createJoinRequest(request.getRoomId(), request.getUserId());
    }

    @GetMapping("/join-requests/{roomId}")
    @Operation(summary = "방 참여 요청 목록")
    public JoinResponseDto getJoinRequestList(@PathVariable Long roomId) {
        List<JoinResponseDto.JoinRequesDetail> joinList = joinRequestService.getJoinRequests(roomId);
        JoinResponseDto response = new JoinResponseDto();
        response.setJoinList(joinList);
        return response;
    }
}
