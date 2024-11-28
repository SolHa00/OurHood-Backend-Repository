package hello.photo.domain.room.controller;

import hello.photo.domain.room.dto.request.RoomCreateRequest;
import hello.photo.domain.room.dto.request.RoomDetailRequest;
import hello.photo.domain.room.dto.request.RoomLeaveRequest;
import hello.photo.domain.room.dto.request.RoomUpdateRequest;
import hello.photo.domain.room.dto.response.RoomCreateResponse;
import hello.photo.domain.room.dto.response.RoomListResponse;
import hello.photo.domain.room.service.RoomService;
import hello.photo.global.response.ApiResponse;
import hello.photo.global.response.DataResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public DataResponseDto<RoomCreateResponse> createRoom(RoomCreateRequest request) {
        return roomService.createRoom(request);
    }

    @PutMapping("/{roomId}")
    public ApiResponse updateRoom(@PathVariable Long roomId, RoomUpdateRequest request){
        return roomService.updateRoom(roomId, request);
    }

    @GetMapping
    public DataResponseDto<RoomListResponse> roomList(String order, String condition, @RequestParam(required = false) String q) {
        return roomService.getRooms(order, condition, q);
    }

    @PostMapping("/{roomId}")
    public ApiResponse enterRoom(@PathVariable Long roomId, @RequestBody RoomDetailRequest request) {
        return roomService.enterRoom(roomId, request.getUserId());
    }

    @DeleteMapping("/{roomId}")
    public ApiResponse deleteRoom(@PathVariable Long roomId) {
        return roomService.deleteRoom(roomId);
    }

    @PostMapping("/{roomId}/leave")
    public ApiResponse leaveRoom(@PathVariable Long roomId, @RequestBody RoomLeaveRequest request) {
        return roomService.leaveRoom(roomId, request.getUserId());
    }
}
