package hello.photo.domain.join.controller;

import hello.photo.domain.join.dto.request.JoinRequestDto;
import hello.photo.domain.join.dto.response.JoinResponseDto;
import hello.photo.domain.join.service.JoinRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/join-requests")
@Tag(name ="Join API", description = "참여 요청 관련 API")
public class JoinRequestController {

    private final JoinRequestService joinRequestService;

    @PostMapping
    @Operation(summary = "방 참여 요청")
    public void joinRequest(@RequestBody JoinRequestDto request) {
        joinRequestService.createJoinRequest(request.getRoomId(), request.getUserId());
    }

    @GetMapping("/{roomId}")
    @Operation(summary = "방 참여 요청 목록")
    public JoinResponseDto getJoinRequestList(@PathVariable Long roomId) {
        List<JoinResponseDto.JoinRequesDetail> joinList = joinRequestService.getJoinRequests(roomId);
        JoinResponseDto response = new JoinResponseDto();
        response.setJoinList(joinList);
        return response;
    }

    //방 참여 요청 처리
    @DeleteMapping("/{joinRequestId}")
    @Operation(summary = "방 참여 요청 처리")
    public ResponseEntity<Void> handleJoinRequest(
            @PathVariable Long joinRequestId,
            @RequestParam String action) {
        joinRequestService.handleJoinRequest(joinRequestId, action);
        return ResponseEntity.noContent().build();
    }
}
