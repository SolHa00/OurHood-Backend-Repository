package hello.photo.controller;

import hello.photo.dto.join.JoinRequestDto;
import hello.photo.service.JoinRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name ="요청 관련 API")
public class JoinRequestController {

    private final JoinRequestService joinRequestService;

    @PostMapping("/join-requests")
    @Operation(summary = "방 참여 요청 API")
    public ResponseEntity<Void> joinRequest(@RequestBody JoinRequestDto request) {
        joinRequestService.createJoinRequest(request.getRoomId(), request.getUserId());
        return ResponseEntity.ok().build();
    }
}
