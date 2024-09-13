package hello.photo.domain.room.controller;

import hello.photo.domain.room.dto.request.RoomCreateRequest;
import hello.photo.domain.room.dto.request.RoomDetailRequest;
import hello.photo.domain.room.dto.response.RoomCreateResponse;
import hello.photo.domain.room.dto.response.RoomListResponse;
import hello.photo.domain.room.service.RoomService;
import hello.photo.global.response.ApiResponse;
import hello.photo.global.response.DataResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rooms")
@Tag(name = "Room API", description = "Room 관련 API")
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    @Operation(summary = "방 생성")
    public DataResponseDto<RoomCreateResponse> createRoom(RoomCreateRequest request) {
        return roomService.createRoom(request);
    }

    @DeleteMapping("/{roomId}")
    @Operation(summary = "방 삭제")
    public ApiResponse deleteRoom(@PathVariable Long roomId) {
        return roomService.deleteRoom(roomId);
    }

    @GetMapping
    @Operation(summary = "방 리스트 조회")
    public DataResponseDto<RoomListResponse> roomList(String order, String condition, @RequestParam(required = false) String q) {
        return roomService.getRooms(order, condition, q);
    }

    @PostMapping("/{roomId}")
    @Operation(summary = "특정 방 입장", description = "방 멤버에 속하면 입장, 속하지 않는다면 참여 요청을 누르는 페이지로 이동")
    public ApiResponse getRoom(@PathVariable Long roomId, @RequestBody RoomDetailRequest request) {
        return roomService.getRoomDetails(roomId, request.getUserId());
    }
}
