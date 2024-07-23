package hello.photo.controller;

import hello.photo.dto.join.JoinRequestDto;
import hello.photo.dto.join.JoinResponseDto;
import hello.photo.service.JoinRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name ="Join API", description = "참여 요청 관련 API")
public class JoinRequestController {

    private final JoinRequestService joinRequestService;

    @PostMapping("/join-requests")
    @Operation(summary = "방 참여 요청 API")
    public ResponseEntity<Void> joinRequest(@RequestBody JoinRequestDto request) {
        joinRequestService.createJoinRequest(request.getRoomId(), request.getUserId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/join-requests/{roomId}")
    @Operation(summary = "방 참여 요청 목록")
    public ResponseEntity<JoinResponseDto> getJoinRequestList(@PathVariable Long roomId) {
        List<JoinResponseDto.JoinRequesDetail> joinList = joinRequestService.getJoinRequests(roomId);
        JoinResponseDto response = new JoinResponseDto();
        response.setJoinList(joinList);
        return ResponseEntity.ok(response);
    }
}
