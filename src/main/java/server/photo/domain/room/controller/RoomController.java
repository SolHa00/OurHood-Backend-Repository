package server.photo.domain.room.controller;

import server.photo.domain.room.dto.request.RoomCreateRequest;
import server.photo.domain.room.dto.request.RoomDetailRequest;
import server.photo.domain.room.dto.request.RoomLeaveRequest;
import server.photo.domain.room.dto.request.RoomUpdateRequest;
import server.photo.domain.room.dto.response.RoomCreateResponse;
import server.photo.domain.room.dto.response.RoomListResponse;
import server.photo.domain.room.service.RoomService;
import server.photo.global.handler.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public BaseResponse<RoomCreateResponse> createRoom(RoomCreateRequest request) {
        return roomService.createRoom(request);
    }

    @PutMapping("/{roomId}")
    public BaseResponse updateRoom(@PathVariable Long roomId, RoomUpdateRequest request){
        return roomService.updateRoom(roomId, request);
    }

    @GetMapping
    public BaseResponse<RoomListResponse> roomList(String order, String condition, @RequestParam(required = false) String q) {
        return roomService.getRooms(order, condition, q);
    }

    @PostMapping("/{roomId}")
    public BaseResponse enterRoom(@PathVariable Long roomId, @RequestBody RoomDetailRequest request) {
        return roomService.enterRoom(roomId, request.getUserId());
    }

    @DeleteMapping("/{roomId}")
    public BaseResponse deleteRoom(@PathVariable Long roomId) {
        return roomService.deleteRoom(roomId);
    }

    @PostMapping("/{roomId}/leave")
    public BaseResponse leaveRoom(@PathVariable Long roomId, @RequestBody RoomLeaveRequest request) {
        return roomService.leaveRoom(roomId, request.getUserId());
    }
}
