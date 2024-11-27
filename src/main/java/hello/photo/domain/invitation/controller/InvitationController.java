package hello.photo.domain.invitation.controller;


import hello.photo.domain.invitation.dto.request.InvitationHandleRequest;
import hello.photo.domain.invitation.dto.request.InvitationRequest;
import hello.photo.domain.invitation.service.InvitationService;
import hello.photo.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/invitations")
public class InvitationController {

    private final InvitationService invitationService;

    @PostMapping
    public ApiResponse createInvitation(@RequestBody InvitationRequest request) {
        return invitationService.createInvitation(request.getRoomId(), request.getNickname());
    }

    @PostMapping("/{invitationId}")
    public ApiResponse handleInvitation(@PathVariable Long invitationId, @RequestBody InvitationHandleRequest request) {
        return invitationService.handleInviteRequest(invitationId, request);
    }
}
