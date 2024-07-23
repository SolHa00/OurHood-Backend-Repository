package hello.photo.controller;


import hello.photo.dto.invitation.InvitationRequest;
import hello.photo.service.InvitationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Invitation API", description = "초대 요청 관련 API")
public class InvitationController {

    private final InvitationService invitationService;

    @PostMapping("/invitations")
    @Operation(summary = "방 초대 요청 API")
    public ResponseEntity<Void> createInvitation(@RequestBody InvitationRequest request) {
        invitationService.createInvitation(request.getRoomId(), request.getNickname());
        return ResponseEntity.ok().build();
    }
}
