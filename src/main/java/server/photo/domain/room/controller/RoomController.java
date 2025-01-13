package server.photo.domain.room.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import server.photo.domain.room.dto.request.RoomCreateRequest;
import server.photo.domain.room.dto.request.RoomDetailRequest;
import server.photo.domain.room.dto.request.RoomLeaveRequest;
import server.photo.domain.room.dto.request.RoomUpdateRequest;
import server.photo.domain.room.dto.response.JoinRequestListResponse;
import server.photo.domain.room.dto.response.RoomCreateResponse;
import server.photo.domain.room.dto.response.RoomInvitationsDto;
import server.photo.domain.room.dto.response.RoomListResponse;
import server.photo.domain.room.service.RoomService;
import server.photo.global.handler.response.BaseResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    //방 생성
    @PostMapping
    public BaseResponse<RoomCreateResponse> createRoom(RoomCreateRequest request) {
        return roomService.createRoom(request);
    }

    //방 수정
    @PutMapping("/{roomId}")
    public BaseResponse<Object> updateRoom(@PathVariable Long roomId, RoomUpdateRequest request){
        return roomService.updateRoom(roomId, request);
    }

    //방 리스트 조회
    @GetMapping
    public BaseResponse<List<RoomListResponse>> roomList(String order, String condition, @RequestParam(required = false) String q) {
        return roomService.getRooms(order, condition, q);
    }

    //특정 방 입장
    @PostMapping("/{roomId}")
    public BaseResponse<Object> enterRoom(@PathVariable Long roomId, @RequestBody RoomDetailRequest request) {
        return roomService.enterRoom(roomId, request.getUserId());
    }

    //방 삭제
    @DeleteMapping("/{roomId}")
    public BaseResponse<Object> deleteRoom(@PathVariable Long roomId) {
        return roomService.deleteRoom(roomId);
    }

    @PostMapping("/{roomId}/leave")
    public BaseResponse<Object> leaveRoom(@PathVariable Long roomId, @RequestBody RoomLeaveRequest request) {
        return roomService.leaveRoom(roomId, request.getUserId());
    }

    // 방에서 보낸 초대 요청 목록 조회
    @GetMapping("/{roomId}/invitations")
    public BaseResponse<RoomInvitationsDto> getInvitations(@PathVariable Long roomId) {
        return roomService.getInvitations(roomId);
    }

    //방 참여 요청 목록 조회
    @GetMapping("/{roomId}/join-requests")
    public BaseResponse<JoinRequestListResponse> getJoinRequestList(@PathVariable Long roomId) {
        return roomService.getJoinRequests(roomId);
    }
}
