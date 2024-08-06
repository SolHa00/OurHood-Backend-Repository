package hello.photo.domain.invitation.controller;


import hello.photo.domain.invitation.dto.request.InvitationRequest;
import hello.photo.domain.invitation.service.InvitationService;
import hello.photo.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/invitations")
@Tag(name = "Invitation API", description = "초대 요청 관련 API")
public class InvitationController {

    private final InvitationService invitationService;

    @PostMapping
    @Operation(summary = "방 초대 요청 API")
    public void createInvitation(@RequestBody InvitationRequest request) {
        invitationService.createInvitation(request.getRoomId(), request.getNickname());
    }

    @DeleteMapping("/{invitationId}")
    @Operation(summary = "방 초대 요청 처리 API")
    public ApiResponse handleInvitation(@PathVariable Long invitationId, String action) {
        return invitationService.handleInviteRequest(invitationId, action);
    }
}
