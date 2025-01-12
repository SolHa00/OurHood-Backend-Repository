package server.photo.domain.invitation.controller;


import server.photo.domain.invitation.dto.request.InvitationHandleRequest;
import server.photo.domain.invitation.dto.request.InvitationRequest;
import server.photo.domain.invitation.service.InvitationService;
import server.photo.global.handler.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/invitations")
public class InvitationController {

    private final InvitationService invitationService;

    @PostMapping
    public BaseResponse createInvitation(@RequestBody InvitationRequest request) {
        return invitationService.createInvitation(request.getRoomId(), request.getNickname());
    }

    @PostMapping("/{invitationId}")
    public BaseResponse handleInvitation(@PathVariable Long invitationId, @RequestBody InvitationHandleRequest request) {
        return invitationService.handleInviteRequest(invitationId, request);
    }

    @DeleteMapping("/{invitationId}")
    public BaseResponse deleteInvitation(@PathVariable Long invitationId) {
        return invitationService.deleteInvitation(invitationId);
    }
}
